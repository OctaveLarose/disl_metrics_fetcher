package disl_metrics_fetcher.helpers;

import ch.usi.dag.disl.util.JavaNames;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import ch.usi.dag.disl.staticcontext.AbstractStaticContext;

import java.util.*;


public class MethodInstructionsContext extends AbstractStaticContext {
    public String getMethodInstrInfoStr() {
        MethodNode method = staticContextData.getMethodNode();

//        System.err.println(staticContextData.getClassNode().interfaces);
//        System.err.println(staticContextData.getClassNode().superName);

        // Note: for loops, GOTOs are also used for try/catch blocks, I believe, so not entirely accurate?
        // TODO: how about only counting GOTOs to previous instructions? We need label offsets though and they're not readable
        return  "---\n"
                + JavaNames.methodName(staticContextData.getClassNode().name, method.name) + "\n"
                + "VARS NBR: " + this.getNbrLocalVars() + "\n"
                + "LOOPS NBR: " + this.countOpcodes(new ArrayList<>(Collections.singletonList(Opcodes.GOTO))) + "\n"
                + "ADD INSTS: " + this.countOpcodes(new ArrayList<>(Arrays.asList(Opcodes.DADD, Opcodes.FADD, Opcodes.IADD, Opcodes.LADD))) + "\n"
                + "SUB INSTS: " + this.countOpcodes(new ArrayList<>(Arrays.asList(Opcodes.DSUB, Opcodes.FSUB, Opcodes.ISUB, Opcodes.LSUB))) + "\n"
                + "MUL INSTS: " + this.countOpcodes(new ArrayList<>(Arrays.asList(Opcodes.DMUL, Opcodes.FMUL, Opcodes.ISUB, Opcodes.LDIV))) + "\n"
                + "DIV INSTS: " + this.countOpcodes(new ArrayList<>(Arrays.asList(Opcodes.DDIV, Opcodes.FDIV, Opcodes.IDIV, Opcodes.LDIV))) + "\n"
                + "---";
    }

    public int countOpcodes(List<Integer> opcodes) {
        MethodNode method = staticContextData.getMethodNode();
        InsnList insList = method.instructions;
        int opcodesNbr = 0;

        for (int i = 0; i < insList.size(); i++) {
            AbstractInsnNode instruction = insList.get(i);

            if (opcodes.contains(instruction.getOpcode())) {
                opcodesNbr++;
            }
        }

        return opcodesNbr;
    }

    public int getNbrLocalVars() {
        MethodNode method = staticContextData.getMethodNode();
        InsnList insList = method.instructions;
        Set<Integer> varIdAccesses = new HashSet<>();

        // The quick and easy method, but inaccurate since returns the max locals list size, not accounting for bigger types like double
        // return method.maxLocals;

        // method.params is null for some reason so here's an ugly hack from the method description string
        // if "L", then it's an object with the full type following, and we assume the full type contains one single capital letter


        for (int i = 0; i < insList.size(); i++) {
            AbstractInsnNode instr = insList.get(i);
            // We count store accesses, but maybe most JVM implementations remove unused variables and thus this is inaccurate? Probably not?
            // Doesn't account for exceptions and probably some other cases. TODO check Run.runBenchmark()
            if ((Opcodes.ISTORE <= instr.getOpcode()) && (instr.getOpcode() <= Opcodes.SASTORE))
                varIdAccesses.add(((VarInsnNode) instr).var);
        }

//        System.err.println(method.attrs);
//        System.err.println(method.parameters);
//        System.err.println(method.desc);

        // When there are no store calls and there are params, it can be negative
        return Math.max(0, varIdAccesses.size() - Utils.getNbrParams(method));
    }


    public int fuckaround() {
        MethodNode method = staticContextData.getMethodNode();
        InsnList insList = method.instructions;

        System.err.println("---METHOD " + staticContextData.getClassNode().name + "." + method.name + " START---");
//        System.err.println("MAXLOCALS: " + method.maxLocals);

        for (int i = 0; i < insList.size(); i++) {
            AbstractInsnNode instruction = insList.get(i);
//            System.err.println(instruction.getOpcode());
//            System.err.println("INSTR: " + instruction);

            if (instruction.getOpcode() == Opcodes.GETSTATIC)
                System.err.println(instruction);
//            if (instruction instanceof VarInsnNode) {
//                System.err.println("Var insn node:" + ((VarInsnNode) instruction).var);
//                System.err.println("OP:" + instruction.getOpcode());
//                System.err.println(instruction.getOpcode());
//            }
//                System.err.println("---LDC INSN START---");
//                System.err.println(("LDC: " + ((LdcInsnNode) instruction).cst));
//                System.err.println(("LDC: " + instruction.getType()));
//                System.err.println(("LDC: " + instruction.getOpcode()));
//                System.err.println("---LDC INSN END---");
//            }
//
//            if (instruction instanceof MethodInsnNode) {
//                System.err.println("---METHOD INSN START---");
//                System.err.println(((MethodInsnNode) instruction).name);
//                System.err.println(((MethodInsnNode) instruction).owner);
//                System.err.println(((MethodInsnNode) instruction).desc);
//                System.err.println(((MethodInsnNode) instruction).itf);
//                System.err.println("---METHOD INSN END---");
//            }

        }
        System.err.println("---METHOD END---");

        return 0;
    }
}
