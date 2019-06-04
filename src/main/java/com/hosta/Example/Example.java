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

	// 液体用のテクスチャは直接登録する
	public static final ResourceLocation	STILL			= new ResourceLocation(Example.MOD_ID, "blocks/molten_example");
	public static final ResourceLocation	FLOW			= new ResourceLocation(Example.MOD_ID, "blocks/molten_example_flow");

	// 液体とBlockを別々に宣言する
	// Blockは液体の登録後に定義する
	public static final Fluid				FLUID_EXAMPLE	= new Fluid("example", Example.STILL, Example.FLOW);
	public static Block						moltenExample;

	static
	{
		// 追加した液体用のバケツを追加する
		// preInit()ではもう遅い、初期化ブロック内に記載する
		// Forgeで共有する設定のため、TiC等のバケツを追加するModを前提Modとする場合は必要ない
		FluidRegistry.enableUniversalBucket();
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		// お馴染みのEvent登録メソッド
		MinecraftForge.EVENT_BUS.register(this);

		// バケツを追加すると見せかけて、液体の登録も行う
		// 上記のFluidRegistry.enableUniversalBucket()をしないと、バケツは追加されない
		FluidRegistry.addBucketForFluid(Example.FLUID_EXAMPLE);
	}

	@SubscribeEvent
	public void registerBlocks(Register<Block> event)
	{
		// 液体が登録された後に定義する
		Example.moltenExample = new BlockFluidClassic(Example.FLUID_EXAMPLE, Material.LAVA).setUnlocalizedName("molten_example");

		// お馴染みのBlock登録
		Example.moltenExample.setRegistryName(Example.moltenExample.getUnlocalizedName().substring(5));
		event.getRegistry().register(Example.moltenExample);
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event)
	{
		// 液体BlockのBlockstate設定
		// Blockの登録と見せかけつつ、実は液体をくんだバケツにも影響する
		ModelLoader.setCustomStateMapper(Example.moltenExample, new StateMapperBase()
		{

			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state)
			{
				// Blockstateのパス設定
				// Blockstate内にどの液体のテクスチャを使うか記載する
				return new ModelResourceLocation(Example.moltenExample.getRegistryName().toString());
			}
		});
	}
}
