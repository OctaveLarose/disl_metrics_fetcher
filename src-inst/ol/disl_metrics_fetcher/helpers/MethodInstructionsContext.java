package disl_metrics_fetcher.helpers;

import ch.usi.dag.disl.util.JavaNames;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import ch.usi.dag.disl.staticcontext.AbstractStaticContext;

import java.util.List;


public class MethodInstructionsContext extends AbstractStaticContext {
    public String getMethodInstrInfoStr() {
        MethodNode method = staticContextData.getMethodNode();

//        System.err.println(staticContextData.getClassNode().interfaces);
//        System.err.println(staticContextData.getClassNode().superName);

        return  "---\n"
                + JavaNames.methodName(staticContextData.getClassNode().name, method.name) + "\n"
                + "VARS NBR: " + this.getNbrLocalVars() + "\n"
                + "LOOPS NBR: " + this.getLoopsNbr() + "\n"
                + "---";
    }

    public int getLoopsNbr() {
        MethodNode method = staticContextData.getMethodNode();
        InsnList insList = method.instructions;
        int loopsNbr = 0;

        for (int i = 0; i < insList.size(); i++) {
            AbstractInsnNode instruction = insList.get(i);

            // Note: also used for try/catch blocks, I believe, so not entirely accurate?
            if (instruction.getOpcode() == Opcodes.GOTO) {
                loopsNbr++;
            }
        }

        return loopsNbr;
    }

    public int getNbrLocalVars() {
        MethodNode method = staticContextData.getMethodNode();
        InsnList insList = method.instructions;
        int varsNbr = 0;

        return (method.maxLocals - 1);

//        for (int i = 0; i < insList.size(); i++) {
//            AbstractInsnNode instruction = ;
//            if (insList.get(i) instanceof VarInsnNode)
//                varsNbr++;
//        }
//        return varsNbr;
    }


    public int fuckaround() {
        MethodNode method = staticContextData.getMethodNode();
        InsnList insList = method.instructions;

        System.err.println("---METHOD " + method.name + " START---");
        System.err.println("MAXLOCALS: " + method.maxLocals);

        for (int i = 0; i < insList.size(); i++) {
            AbstractInsnNode instruction = insList.get(i);
            System.err.println(instruction.getOpcode());

            if (instruction instanceof LdcInsnNode) {
                System.err.println("---LDC INSN START---");
                System.err.println(("LDC: " + ((LdcInsnNode) instruction).cst));
                System.err.println(("LDC: " + instruction.getType()));
                System.err.println(("LDC: " + instruction.getOpcode()));
                System.err.println("---LDC INSN END---");
            }

            if (instruction instanceof MethodInsnNode) {
                System.err.println("---METHOD INSN START---");
                System.err.println(((MethodInsnNode) instruction).name);
                System.err.println(((MethodInsnNode) instruction).owner);
                System.err.println(((MethodInsnNode) instruction).desc);
                System.err.println(((MethodInsnNode) instruction).itf);
                System.err.println("---METHOD INSN END---");
            }

        }
        System.err.println("---METHOD END---");

        return 0;
    }
}
