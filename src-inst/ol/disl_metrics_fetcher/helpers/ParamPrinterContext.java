package disl_metrics_fetcher.helpers;

import ch.usi.dag.disl.staticcontext.AbstractStaticContext;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class ParamPrinterContext extends AbstractStaticContext {

    public int modifyMethodToPrint() {
        MethodNode method = staticContextData.getMethodNode();
        String methodDesc = staticContextData.getMethodNode().desc;
        InsnList insList = method.instructions;

        System.err.println("Modifying: "
                + staticContextData.getClassNode().name + "."
                + methodDesc
        );

//        Code:
//        stack=3, locals=3, args_size=2
//        0: getstatic     #2                  // Field java/lang/System.err:Ljava/io/PrintStream;
//        3: lload_1
//        4: invokevirtual #3                  // Method java/io/PrintStream.println:(J)V
//        7: return

        if (methodDesc.contains("J")) {
            if (Utils.getNbrParams(method) >= 1) {
                insList.insert(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
                        "java/io/PrintStream",
                        "println",
                        "(J)V"));
                insList.insert(new VarInsnNode(Opcodes.LLOAD, 1));
                insList.insert(new FieldInsnNode(Opcodes.GETSTATIC,
                        "java/lang/System",
                        "err",
                        "Ljava/io/PrintStream;"));
            }
        }

        return 0;
    }
}
