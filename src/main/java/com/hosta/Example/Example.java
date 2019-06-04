package com.hosta.Example;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Example.MOD_ID, name = "Example", version = "0.0.1")
public class Example {

	public static final String				MOD_ID			= "example";

	// �t�̗p�̃e�N�X�`���͒��ړo�^����
	public static final ResourceLocation	STILL			= new ResourceLocation(Example.MOD_ID, "blocks/molten_example");
	public static final ResourceLocation	FLOW			= new ResourceLocation(Example.MOD_ID, "blocks/molten_example_flow");

	// �t�̂�Block��ʁX�ɐ錾����
	// Block�͉t�̂̓o�^��ɒ�`����
	public static final Fluid				FLUID_EXAMPLE	= new Fluid("example", Example.STILL, Example.FLOW);
	public static Block						moltenExample;

	static
	{
		// �ǉ������t�̗p�̃o�P�c��ǉ�����
		// preInit()�ł͂����x���A�������u���b�N���ɋL�ڂ���
		// Forge�ŋ��L����ݒ�̂��߁ATiC���̃o�P�c��ǉ�����Mod��O��Mod�Ƃ���ꍇ�͕K�v�Ȃ�
		FluidRegistry.enableUniversalBucket();
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		// ������݂�Event�o�^���\�b�h
		MinecraftForge.EVENT_BUS.register(this);

		// �o�P�c��ǉ�����ƌ��������āA�t�̂̓o�^���s��
		// ��L��FluidRegistry.enableUniversalBucket()�����Ȃ��ƁA�o�P�c�͒ǉ�����Ȃ�
		FluidRegistry.addBucketForFluid(Example.FLUID_EXAMPLE);
	}

	@SubscribeEvent
	public void registerBlocks(Register<Block> event)
	{
		// �t�̂��o�^���ꂽ��ɒ�`����
		Example.moltenExample = new BlockFluidClassic(Example.FLUID_EXAMPLE, Material.LAVA).setUnlocalizedName("molten_example");

		// ������݂�Block�o�^
		Example.moltenExample.setRegistryName(Example.moltenExample.getUnlocalizedName().substring(5));
		event.getRegistry().register(Example.moltenExample);
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event)
	{
		// �t��Block��Blockstate�ݒ�
		// Block�̓o�^�ƌ��������A���͉t�̂����񂾃o�P�c�ɂ��e������
		ModelLoader.setCustomStateMapper(Example.moltenExample, new StateMapperBase()
		{

			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state)
			{
				// Blockstate�̃p�X�ݒ�
				// Blockstate���ɂǂ̉t�̂̃e�N�X�`�����g�����L�ڂ���
				return new ModelResourceLocation(Example.moltenExample.getRegistryName().toString());
			}
		});
	}
}
