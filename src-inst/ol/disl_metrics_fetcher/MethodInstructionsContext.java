package disl_metrics_fetcher;

import ch.usi.dag.disl.util.JavaNames;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import ch.usi.dag.disl.staticcontext.AbstractStaticContext;

import java.util.List;


public class MethodInstructionsContext extends AbstractStaticContext {
    public String getTotalLocalVarsStr() {
        MethodNode method = staticContextData.getMethodNode();

        return JavaNames.methodName(staticContextData.getClassNode().name, method.name)
                + " "
                + (method.maxLocals - 1)
                + "\n";

//        System.out.println("---START---");
//        System.out.println("NAME: " + method.name);
//        System.out.println("MAXLOCALS: " + method.maxLocals);
//        System.out.println("MAXSTACK " + method.maxStack);
//        System.out.println("LOCAL VARS ARRAY: " + method.localVariables);
//        for (final LocalVariableNode local : method.localVariables) {
//            System.out.println("Local var: " + local.name + " : " + local.desc + " : " + local.signature + " : " + local.index);
//        }
//        System.out.println("---END---\n");
    }

    public boolean hasLoop() {
        MethodNode method = staticContextData.getMethodNode();

        InsnList insList = method.instructions;

        for (int i = 0; i < insList.size(); i++) {
            AbstractInsnNode instruction = insList.get(i);
//
//            if (instruction instanceof LdcInsnNode) {
//                System.out.println("---LDC INSN START---");
//                System.out.println(("LDC: " + ((LdcInsnNode) instruction).cst));
//                System.out.println(("LDC: " + ((LdcInsnNode) instruction).getType()));
////                System.out.println(("LDC: " + ((LdcInsnNode) instruction).getOpcode()));
//                System.out.println("---LDC INSN END---");
//            }
////            if (instruction instanceof MethodInsnNode) {
////                System.out.println("---METHOD INSN START---");
////                System.out.println(((MethodInsnNode) instruction).name);
////                System.out.println(((MethodInsnNode) instruction).owner);
////                System.out.println(((MethodInsnNode) instruction).desc);
////                System.out.println(((MethodInsnNode) instruction).itf);
////                System.out.println("---METHOD INSN END---");
////            }
//
            if (instruction.getOpcode() == Opcodes.GOTO) {
//                System.out.println("ENDT");
                return true;
            }
        }
//        System.out.println("ENDF\n\n");
            return false;
    }
}
