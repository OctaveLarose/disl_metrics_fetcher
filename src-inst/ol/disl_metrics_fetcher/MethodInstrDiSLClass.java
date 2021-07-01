import ch.usi.dag.disl.annotation.After;
import ch.usi.dag.disl.annotation.Before;
import ch.usi.dag.disl.annotation.GuardMethod;
import ch.usi.dag.disl.annotation.SyntheticLocal;
import ch.usi.dag.disl.dynamiccontext.DynamicContext;
import ch.usi.dag.disl.marker.BodyMarker;
import ch.usi.dag.disl.staticcontext.MethodStaticContext;

import java.io.FileWriter;
import java.io.IOException;

public class MethodInstrDiSLClass {

//    @Before (marker=BodyMarker.class, guard = TestGuard.class)
//    @After(marker= BodyMarker.class, guard = NonDefaultGuard.class)
//    static void conMethodExit(MethodInstructionsContext dlc, MethodStaticContext msc, DynamicContext di) {
//        try {
////            logFile.write(dlc.getTotalLocalVarsStr());
//            logFile.write(dlc.getLoopsNbrStr());
//            logFile.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println(dlc.getTotalLocalVarsStr());
//        System.out.println(dlc.getTotalLocalVarsStr());
//        dlc.getLoopsNbrStr();
//        if (msc.thisMethodFullName().equals("Bounce$Ball.bounce")) {
//            System.out.println("VARS:");
//            System.out.println(di.getLocalVariableValue(0, Object.class).getClass().getTypeName());
//            System.out.println(di.getLocalVariableValue(1, int.class));
//            System.out.println("\n");
//        }
//        if (dlc.hasLoop()) {
//            System.out.printf("disl: %s had loop\n", msc.thisMethodName());
//        } else {
//            System.out.printf("disl: %s had no loop\n", msc.thisMethodName());
//        }
//    }

    @After(marker = BodyMarker.class, scope = "Harness.main")
    public static void afterInstance(DynamicContext di) {
        Object o = di.getThis();
        System.out.println("disl: this=" + o.toString());
    }

//    @After(marker = BodyMarker.class, scope = "[default].*.*(..)")
//    public static void afterException(DynamicContext di) {
//        Throwable o = di.getException();
//        System.out.println("disl: exception=" + o.getMessage());
//    }

    public static final class NonDefaultGuard {
        @GuardMethod
        public static boolean isNotDefault(MethodStaticContext context) {
            String[] blacklist = {"java", "sun", "jdk"};

            for (String bannedStr: blacklist) {
                if (context.thisMethodFullName().startsWith(bannedStr))
                        return false;
            }
            return true;
        }
    }
}
