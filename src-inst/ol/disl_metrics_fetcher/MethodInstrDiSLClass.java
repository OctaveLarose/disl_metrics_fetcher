import ch.usi.dag.disl.annotation.*;
import ch.usi.dag.disl.dynamiccontext.DynamicContext;
import ch.usi.dag.disl.marker.BodyMarker;
import ch.usi.dag.disl.staticcontext.MethodStaticContext;
import disl_metrics_fetcher.helpers.MethodInstructionsContext;

public class MethodInstrDiSLClass {
    @After(marker= BodyMarker.class, guard = NonDefaultGuard.class)
    static void conMethodExit(MethodInstructionsContext dlc, MethodStaticContext msc, DynamicContext di) {
        // Only printing on stderr to get to redirect the program's output elsewhere
        System.err.println(dlc.getMethodInstrInfoStr());
        //        dlc.fuckaround();
    }

//    @After(marker=VarInsnMarker.class, guard = NonDefaultGuard.class)
//    static void aa(MethodInstructionsContext dlc, MethodStaticContext msc, DynamicContext di) {
//        // Only printing on stderr to get to redirect the program's output elsewhere
//        System.err.println(dlc.getMethodInstrInfoStr());
////        dlc.fuckaround();
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
