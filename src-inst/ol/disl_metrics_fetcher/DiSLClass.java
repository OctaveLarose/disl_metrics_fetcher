import ch.usi.dag.disl.annotation.After;
import ch.usi.dag.disl.annotation.Before;
import ch.usi.dag.disl.annotation.GuardMethod;
import ch.usi.dag.disl.annotation.SyntheticLocal;
import ch.usi.dag.disl.dynamiccontext.DynamicContext;
import ch.usi.dag.disl.marker.BodyMarker;
import ch.usi.dag.disl.staticcontext.MethodStaticContext;
import disl_metrics_fetcher.MethodInvocationMarker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

public class DiSLClass {

    @SyntheticLocal
    static long entryTime;

    @SyntheticLocal
    static FileWriter methodLogFile;

    static {
        try {
            methodLogFile = new FileWriter("methodLogFile.txt", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @After(marker = BodyMarker.class, scope = "*")
//    public static void afterMul(DynamicContext di) {
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
    @Before (marker=BodyMarker.class, guard = TestGuard.class)
    static void conMethodEntry(MethodStaticContext msc) {
        try {
            entryTime = System.nanoTime();
            methodLogFile.write("> " + msc.thisMethodFullName() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    @Before (marker=BodyMarker.class, scope = "[default].*.*(..)")
    @After (marker=BodyMarker.class, guard = TestGuard.class)
    static void conMethodExit(MethodStaticContext msc) {
        try {
            methodLogFile.write(
                    "< " + msc.thisMethodFullName() + " "
                    + "(" + (System.nanoTime() - entryTime) + " ns)"
                    + "\n");
            methodLogFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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