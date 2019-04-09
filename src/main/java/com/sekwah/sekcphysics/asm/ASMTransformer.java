package com.sekwah.sekcphysics.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import javax.annotation.Nullable;
import java.util.List;
import java.util.ListIterator;

/*
 Used as reference https://github.com/elucent/Albedo/blob/38b7a2b70b9501f09106d3f670976bb56d321f31/src/main/java/elucent/albedo/asm/ASMTransformer.java

 Basically had exactly what was needed anyway ¯\_(ツ)_/¯
 */
public class ASMTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (transformedName.equals("net.minecraft.profiler.Profiler")) {
            return patchProfiler(name, basicClass, name.compareTo(transformedName) != 0);
        }
        return basicClass;
    }


    public byte[] patchProfiler(String name, byte[] bytes, boolean obfuscated) {
        String targetMethod = "";
        if (obfuscated){
            targetMethod = "func_76318_c";
        }
        else {
            targetMethod = "endStartSection";
        }

        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);

        List<MethodNode> methods = classNode.methods;

        for (MethodNode m : methods){
            if (m.name.compareTo(targetMethod) == 0 && m.desc.compareTo("(Ljava/lang/String;)V") == 0){
                InsnList code = m.instructions;
                List<LocalVariableNode> vars = m.localVariables;
                int paramloc = -1;
                //if (!obfuscated){
                paramloc = 1;
				/*}
				for (int i = 0; i < vars.size() && paramloc == -1; i ++){
					LocalVariableNode p = vars.get(i);
					if (p.desc.compareTo("Ljava/lang/String;") == 0){
						paramloc = i;
					}
				}*/
                @Nullable AbstractInsnNode returnNode = null;
                for(ListIterator<AbstractInsnNode> iterator = code.iterator(); iterator.hasNext(); ) {
                    AbstractInsnNode insn = iterator.next();

                    if(insn.getOpcode() == Opcodes.RETURN) {
                        returnNode = insn;
                        break;
                    }
                }

                if(paramloc > -1) {
                    MethodInsnNode method = new MethodInsnNode(Opcodes.INVOKESTATIC, "com/sekwah/sekcphysics/client/RagdollRenderer",
                            "renderRagdolls", "(Ljava/lang/String;)V", false);
                    code.insertBefore(code.get(2), method);
                    code.insertBefore(code.get(2), new VarInsnNode(Opcodes.ALOAD, paramloc));
                    System.out.println("Successfully loaded Profiler ASM!");
                }
                else {
                }
            }
        }

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        classNode.accept(writer);
        return writer.toByteArray();
    }

}
