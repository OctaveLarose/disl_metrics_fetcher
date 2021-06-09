import ch.usi.dag.disl.annotation.*;
import ch.usi.dag.disl.annotation.ThreadLocal;
import ch.usi.dag.disl.dynamiccontext.DynamicContext;
import ch.usi.dag.disl.marker.BodyMarker;
import ch.usi.dag.disl.staticcontext.MethodStaticContext;
import disl_metrics_fetcher.MethodInstructionsContext;
import disl_metrics_fetcher.MethodInvocationMarker;
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

//    @After(marker = BodyMarker.class)
//    public static void getLocalVariables(DynamicContext di) {
//        int a = di.getLocalVariableValue(0, int.class);
//        int b = di.getLocalVariableValue(1, int.class);
//        System.out.println("disl: a=" + a);
//        System.out.println("disl: b=" + b);
//    }

//    @Before(marker = BodyMarker.class, scope = "Harness.main")
//    public static void afterInvocation(DynamicContext di) {
//        System.out.println("disl: before invocation");
//        System.out.println(di.getStackValue(0, Object.class));
////        System.out.println(di.getLocalVariableValue(0, int.class));
//    }

//    @Before(marker = BodyMarker.class, scope = "[default].*.*(..)", guard = ConstructorGuard.class)
//    @Before(marker = BodyMarker.class, scope = "[default].*.*(..)")
//    public static void beforeInvocation(MethodStaticContext m) {
//        System.out.println("aaa");
//        System.out.println("disl: before invocation " + m.thisMethodFullName() + " " + m.thisMethodSignature() + " " + m.thisMethodDescriptor() + ".");
//    }

//    @Before (marker=BodyMarker.class, scope = "[default].*.*(..)")
//    @Before (marker=BodyMarker.class, guard = TestGuard.class)
//    static void conMethodEntry(MethodStaticContextPrinter mscp) {
//        try {
//            entryTime = System.nanoTime();
//            methodLogFile.write(mscp.getMethodEntryStr());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    @Before (marker=BodyMarker.class, guard = TestGuard.class)
//    @After(marker= BodyMarker.class, guard = TestGuard.class)
    @After(marker= BodyMarker.class, guard = TestGuard.class)
    static void conMethodExit(MethodInstructionsContext dlc, MethodStaticContext msc, DynamicContext di) {
//        try {
//            logFile.write(dlc.getTotalLocalVarsStr());
//            logFile.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        dlc.hasLoop();
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
    }

    //    @Before (marker=BodyMarker.class, scope = "[default].*.*(..)")
//    @After (marker=BodyMarker.class, guard = TestGuard.class)
//    static void conMethodExit(MethodStaticContextPrinter mscp) {
//        try {
//            methodLogFile.write(mscp.getMethodExitStr());
//            methodLogFile.close();
//        } catch (IOException e) {
//            e.printStackTrace();
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

    public static final class TestGuard {
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

//    public static class ConstructorGuard {
//        @GuardMethod
//        public static boolean isApplicable(MethodStaticContext sc) {
//            return "<init>".equals(sc.thisMethodName());
//        }
//    }
}