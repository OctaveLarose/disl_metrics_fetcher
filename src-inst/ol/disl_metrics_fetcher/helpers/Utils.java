package disl_metrics_fetcher.helpers;

import org.objectweb.asm.tree.MethodNode;

public class Utils {
    static public int getNbrParams(MethodNode method) {
        String paramsStr = method.desc.substring(1, method.desc.indexOf(')'));

        return (int) paramsStr.chars().filter(a -> ((a >= 'A') && (a <= 'Z'))).count() -
                (int) paramsStr.chars().filter(a -> a == 'L').count();
    }
}
