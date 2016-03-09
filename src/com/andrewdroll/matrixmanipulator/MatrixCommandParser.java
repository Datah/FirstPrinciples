/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andrewdroll.matrixmanipulator;

import com.andrewdroll.matrixmanipulator.Matrix.MatrixOpException;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Andrew
 */
public class MatrixCommandParser {

    public static final Pattern MPAT = Pattern.compile("[.]");
    public static final Pattern DPAT = Pattern.compile("[,]");
    public static final Pattern OP_ARG_PAT = Pattern.compile("([,(][\\d]+([.]\\d+)?)*[)]$");
    public static final Pattern NUMPAT = Pattern.compile("[\\d]+([.]\\d+)?");

    public static final Pattern MNAMEPAT = Pattern.compile("(^|[^\\w])(\\p{Upper})([^\\w]|$)");

    MatrixNameMap matrices = new MatrixNameMap();

    public static enum BasisCommands {

        Invert("Inv", "OP", Matrix.class, "invert"),
        Transpose("Trans", "OP", Matrix.class, "transpose"),
        Determinant("Det", "OP", Matrix.class, "scalarDeterminantMe"),
        RREF("RREF", "OP", Matrix.class, "RREF"),
        CoFactor("CoFactor", "OP", Matrix.class, "coFactorSelf", int.class, int.class),
        SubMatrix("SubMatrix", "OP", Matrix.class, "subMatrix", int[].class, int[].class),
        Power("Power", "OP", Matrix.class, "power", int.class),
        Scale("Scale", "OP", Matrix.class, "mult", double.class),
        RandomMatrix("RandomMatrix", "MK", Matrix.class, "random", int.class, int.class),
        ReadMatrix("ReadMatrix", "\\[\\s*(<\\s*(\\d*(\\.\\d+)?\\s*)+>\\s*)\\s*\\]", "MK", Matrix.class, "readMatrix", double[][].class),
        FromFile("FromFile", "\"\\w+[.]\\w+\"", "MK", Matrix.class, "fromFile", File.class),
        NONE;

        public final String preface;
        public final Method method;
        public final String type;
        public final ArrayList<Class<?>> parameterTypes;

        private Pattern argPat;

        BasisCommands() {
            this(null, "OP", null, null);
        }

        BasisCommands(String preface, String type, Class<?> c, String method, Class<?>... parameterTypes) {
            this(preface, null, type, c, method, parameterTypes);
        }

        BasisCommands(String preface, String argPatBase, String type, Class<?> c, String method, Class<?>... parameterTypes) {
            this.preface = preface;
            this.type = type;
            Method meth = null;
            try {
                if (method != null && c != null) {
                    //System.out.println("Getting method " + method + " from class " + c);
                    meth = c.getDeclaredMethod(method, parameterTypes);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            this.method = meth;
            if (argPatBase != null) {
                argPat = Pattern.compile(argPatBase);
            }
            this.parameterTypes = new ArrayList<Class<?>>();
            if (parameterTypes != null) {
                for (Class<?> clazz : parameterTypes) {
                    this.parameterTypes.add(clazz);
                }
            }
        }

        /**
         * @return the argPat
         */
        Pattern getArgPat() {
            return argPat;
        }

    }

    public MatrixCommandParser() {
    }
    
    public Matrix interpretAndAssign(String command){
        Matrix response = null;
        if (command.contains("=")) {
            String[] split = command.split("=");
            if (split.length == 2) {
                String id = split[0].trim();
                String mCom = split[1].trim();
                Matcher nMatcher = MNAMEPAT.matcher(id);
                response = parse(mCom).exec();
                if(nMatcher.matches() && response != null){
                    matrices.getMap().put(id, response);
                }
            }
        }else{
            response = parse(command).exec();
        }
        return response;
    }

    public CommandBlock parse(String command) {

        

        return new CommandBlock(command);
    }

    public class CommandBlock {

        private ArrayList<CommandBlock> subBlocks = new ArrayList<>();

        private CommandBlock innerBlock;

        private BasisCommands outerType;

        private boolean block;

        private String outerTextLeft;

        private String blockText;

        private int level;

        public CommandBlock(String blockText) {
            this(blockText, 0);
        }

        public CommandBlock(String blockText, int level) {
            blockText = blockText.replaceAll("\\s+", "");
            this.blockText = blockText;
            this.level = level;
            processText();

        }

        public final void processText() {
            try {
                processBlockText();
            } catch (CommandFormatException ex) {
                System.out.println("ERROR - Exception " + ex + " --- " + level);
                ex.printStackTrace();
            } catch (Exception ex) {
                System.out.println("ERROR - Unexpected exception " + ex + " --- " + level);
                ex.printStackTrace();
            }
        }

        public Matrix exec() {

            try {
                if (!getSubBlocks().isEmpty()) {
                    Matrix result = null;
                    for (CommandBlock block : getSubBlocks()) {
                        if (result == null) {
                            result = block.exec();
                        } else {
                            result.rightMultiply(block.exec());
                        }
                    }
                    return result;
                } else if (getOuterType() == null) {
                    Matcher mNameMatcher = MNAMEPAT.matcher(getBlockText());
                    if (mNameMatcher.find()) {
                        String name = mNameMatcher.group(2);
                        if (matrices.getMap().containsKey(name)) {
                            Matrix newM = new Matrix();
                            newM.readMatrix(matrices.getMap().get(name).entries);
                            return newM;
                        } else {
                            throw new CommandFormatException("Name " + getBlockText() + " has not yet been assigned --- " + level);
                        }
                    }
                } else if (getOuterType() != null && getOuterType().type.equals("MK")) {
                    String arg = getBlockText().replaceAll(outerType.preface, "").replaceAll("[()]", "");
                    arg = arg.trim();
                    if (getOuterType() == BasisCommands.FromFile) {

                        return Matrix.fromFile(new File(arg));
                    } else if (getOuterType() == BasisCommands.ReadMatrix) {
                        Matcher m = getOuterType().argPat.matcher(arg);
                        if (!m.matches()) {
                            throw new CommandFormatException("Illegal matrix format. --- " + level);
                        } else {
                            return null;
                        }
                    } else if (getOuterType() == BasisCommands.RandomMatrix) {
                        ArrayList<String> args = getStringArgs();
                        Integer[] argObjs = new Integer[args.size()];
                        for (int i = 0; i < args.size(); i++) {
                            argObjs[i] = Integer.parseInt(args.get(i));
                            //System.out.println(argObjs[i]);
                        }
                        return Matrix.random(argObjs[0], argObjs[1]);//(Matrix) getOuterType().method.invoke(null, args);
                    }
                } else if (getOuterType() != null && getOuterType().type.equals("OP") && innerBlock != null) {

                    Matrix result = innerBlock.exec();

                    if (getOuterType() != BasisCommands.NONE) {
                        ArrayList<String> args = getStringArgs();
                        Object[] argObjs = new Object[args.size()];
                        //System.out.println("argS: " + args.size());
                        for (int i = 0; i < args.size(); i++) {
                            if (args.get(i).contains(".")) {
                                double objArg = Double.parseDouble(args.get(i));
                                argObjs[i] = objArg;
                            } else {
                                int objArg = Integer.parseInt(args.get(i));
                                argObjs[i] = objArg;
                            }
                            //System.out.println(argObjs[i]);
                        }
                        //Method meth = getOuterType().method;
                        //System.out.println("num params expected for method " + meth.getName() + ": " + meth.getParameterCount());
                        getOuterType().method.invoke(result, argObjs);
                    }
                    return result;
                } else {
                    throw new CommandFormatException("Other error in parsing command and computing result --- " + level);
                }

            } catch (MatrixOpException ex) {
                System.out.println("ERROR - Exception " + ex + " --- " + level);
                ex.printStackTrace();
            } catch (Exception ex) {
                System.out.println("ERROR - Unexpected exception " + ex + " --- " + level);
                ex.printStackTrace();
            }

            return null;
        }

        private ArrayList<String> getStringArgs() {

            ArrayList<String> toReturn = new ArrayList<>();
            Matcher m = OP_ARG_PAT.matcher(getBlockText());
            if (m.find()) {
                String text = m.group(0);
                String[] split = text.split(",");
                for (String item : split) {
                    item = item.trim();
                    if (!item.equals("")) {
                        item = item.replaceAll("[()]", "");
                        item = item.trim();
                        if (NUMPAT.matcher(item).matches()) {
                            toReturn.add(item);
                        }
                    }
                }
            }
            return toReturn;

        }

        /**
         * @return the subBlocks
         */
        ArrayList<CommandBlock> getSubBlocks() {
            return subBlocks;
        }

        private void processBlockText() throws CommandFormatException {
            char[] blockChars = getBlockText().toCharArray();
            int numOpenParenths = 0;

            int numPeriodBlocks = 0;

            int lastStart = 0;

            for (int i = 0; i < blockChars.length; i++) {

                char curr = blockChars[i];

                //Check for open parenthesis and increment
                if (curr == '(') {
                    numOpenParenths++;
                }

                //Check for close parenthesis and decrement
                if (curr == ')') {
                    numOpenParenths--;
                }

                //Check for matrix multiplication where there are no open parentheses
                if (curr == '*' && numOpenParenths == 0) {
                    if (i < blockChars.length - 1 && i > 0) {
                        String blockToAdd = getBlockText().substring(lastStart, i);
                        getSubBlocks().add(new CommandBlock(blockToAdd, level + 1));
                        lastStart = i + 1;
                        numPeriodBlocks++;
                    } else {
                        throw new CommandFormatException("Illegal multiplication.");
                    }
                }

                if (i == blockChars.length - 1 && lastStart > 0) {
                    String blockToAdd = getBlockText().substring(lastStart, i + 1);
                    getSubBlocks().add(new CommandBlock(blockToAdd, level + 1));
                    numPeriodBlocks++;
                }
            }

            if (numOpenParenths != 0) {
                throw new CommandFormatException("Parenthesis mismatch.");
            }

            if (numPeriodBlocks == 0) {
                block = true;
                int parenthInd = blockText.indexOf('(');
                if (parenthInd >= 0) {
                    int endInd = blockText.lastIndexOf(')');
                    String innerText = blockText.substring(parenthInd + 1, endInd).replaceAll("(,[\\d]+([.]\\d+)?)*$", "");
                    innerBlock = new CommandBlock(innerText, level + 1);
                    if (parenthInd > 0) {
                        outerTextLeft = blockText.substring(0, parenthInd);
                        outerType = BasisCommands.NONE;

                    } else {
                        outerTextLeft = "";
                    }
                    for (BasisCommands cmd : BasisCommands.values()) {
                        if (cmd != BasisCommands.NONE) {
                            if (getOuterTextLeft().startsWith(cmd.preface)) {
                                outerType = cmd;
                            }
                        }
                    }
                } else {
                    outerTextLeft = null;
                    outerType = null;
                }
            } else {
                block = false;
            }
        }

        @Override
        public String toString() {
            String toReturn = "";
            toReturn += "{ COMMAND BLOCK ---- \"outerType\":\"" + getOuterType() + "\",\n\"blockText\":\"" + getBlockText() + "\",\n"
                    + "\"outerTextLeft\":\"" + getOuterTextLeft() + "\",\n\"subBlocks\":" + getSubBlocks() + ",\n"
                    + "\"innerBlock\":" + innerBlock + "\n}";
            return toReturn;
        }

        /**
         * @return the outerType
         */
        BasisCommands getOuterType() {
            return outerType;
        }

        /**
         * @return the outerTextLeft
         */
        String getOuterTextLeft() {
            return outerTextLeft;
        }

        /**
         * @return the blockText
         */
        String getBlockText() {
            return blockText;
        }

        /**
         * @return the level
         */
        int getLevel() {
            return level;
        }

        /**
         * @return the block
         */
        boolean isBlock() {
            return block;
        }

    }

    public static class CommandFormatException extends Exception {

        public CommandFormatException() {
            super();
        }

        public CommandFormatException(String msg) {
            super(msg);
        }
    }

    public static void main(String[] args) {
        MatrixCommandParser parser = new MatrixCommandParser();
        CommandBlock mainBlock = parser.parse("Power(RandomMatrix(2,2),3)");
        //mainBlock.processText();
        Matrix result = mainBlock.exec();
        System.out.println(mainBlock);
        System.out.println(result);
    }

}
