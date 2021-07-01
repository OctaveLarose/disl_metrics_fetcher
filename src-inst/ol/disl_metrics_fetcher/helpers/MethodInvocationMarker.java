package disl_metrics_fetcher.helpers;

import java.util.LinkedList;
import java.util.List;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import ch.usi.dag.disl.marker.AbstractMarker;
import ch.usi.dag.disl.marker.AbstractDWRMarker;

/**
 * Sets the region on every method invocation instruction.
 * 
 */
public class MethodInvocationMarker extends AbstractDWRMarker {

	public List<MarkedRegion> markWithDefaultWeavingReg(MethodNode method) {
		List<MarkedRegion> regions = new LinkedList<MarkedRegion>();

		// traverse all instructions
		InsnList instructions = method.instructions;

		for (AbstractInsnNode instruction : instructions.toArray()) {
//		    System.out.println(method.localVariables);
			// check for method invocation instructions
			if (instruction instanceof MethodInsnNode) {
//			    System.out.println("---");
//                System.out.println(((MethodInsnNode) instruction).name);
//                System.out.println(((MethodInsnNode) instruction).owner);
//                System.out.println(((MethodInsnNode) instruction).desc);
//                System.out.println(((MethodInsnNode) instruction).itf);
//                System.out.println("---");
                // add region containing one instruction (method invocation)
				regions.add(new MarkedRegion(instruction, instruction));
//				System.out.println(instruction);
			}
		}
		return regions;
	}
}
