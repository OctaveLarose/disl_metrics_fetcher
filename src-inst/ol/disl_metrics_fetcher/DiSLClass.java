import ch.usi.dag.disl.annotation.*;
import ch.usi.dag.disl.dynamiccontext.DynamicContext;
import ch.usi.dag.disl.marker.BodyMarker;
import ch.usi.dag.disl.staticcontext.MethodStaticContext;
import disl_metrics_fetcher.MethodInstructionsContext;
import disl_metrics_fetcher.MethodStaticContextPrinter;

import java.io.FileWriter;
import java.io.IOException;

public class DiSLClass {

    @SyntheticLocal
    static long entryTime;

    @SyntheticLocal
    static FileWriter logFile;

    static {
        try {
            logFile = new FileWriter("logfile.txt", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Before (marker=BodyMarker.class, guard = NonDefaultGuard.class)
    static void conMethodEntry(MethodStaticContextPrinter mscp) {
        try {
            entryTime = System.nanoTime();
            logFile.write(mscp.getMethodEntryStr() + "\n");
            logFile.flush();
//            System.out.println(mscp.getMethodEntryStr() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @After (marker=BodyMarker.class, guard = NonDefaultGuard.class)
    static void conMethodExit(MethodStaticContextPrinter mscp) {
        try {
            logFile.write(mscp.getMethodExitStr() + " (" + (System.nanoTime() - entryTime) + "ns)\n");
            logFile.flush();
//        System.out.println(mscp.getMethodExitStr() + " (" + (System.nanoTime() - entryTime) + "ns)\n");
            logFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

//    @After(marker = BodyMarker.class, scope = "Main.testInstance")
//    public static void afterInstance(DynamicContext di) {
//        Object o = di.getThis();
//        System.out.println("disl: this=" + o.toString());
//    }

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
