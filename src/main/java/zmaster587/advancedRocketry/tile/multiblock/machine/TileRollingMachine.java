package zmaster587.advancedRocketry.tile.multiblock.machine;

import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.capability.IFluidHandler;
import zmaster587.advancedRocketry.api.AdvancedRocketryBlocks;
import zmaster587.advancedRocketry.inventory.TextureResources;
import zmaster587.advancedRocketry.util.AudioRegistry;
import zmaster587.libVulpes.api.LibVulpesBlocks;
import zmaster587.libVulpes.api.material.AllowedProducts;
import zmaster587.libVulpes.api.material.Material;
import zmaster587.libVulpes.api.material.MaterialRegistry;
import zmaster587.libVulpes.block.BlockMeta;
import zmaster587.libVulpes.interfaces.IRecipe;
import zmaster587.libVulpes.inventory.modules.ModuleBase;
import zmaster587.libVulpes.inventory.modules.ModuleProgress;
import zmaster587.libVulpes.recipe.RecipesMachine;
import zmaster587.libVulpes.tile.multiblock.TileMultiblockMachine;

public class TileRollingMachine extends TileMultiblockMachine {

	public static final Object structure[][][] = new Object[][][] { 
		{   {'c', null, Blocks.AIR, Blocks.AIR},
			{'I', Blocks.AIR, LibVulpesBlocks.blockStructureBlock, Blocks.AIR},
			{'I', Blocks.AIR, LibVulpesBlocks.blockStructureBlock, Blocks.AIR}},

			{{'P', 'L', LibVulpesBlocks.blockStructureBlock, null},
				{new BlockMeta(Block.getBlockFromItem(MaterialRegistry.getMaterialFromName("Copper").getProduct(AllowedProducts.getProductByName("COIL")).getItem()), MaterialRegistry.getMaterialFromName("Copper").getMeta()), LibVulpesBlocks.blockStructureBlock, LibVulpesBlocks.blockStructureBlock, 'O'},
				{new BlockMeta(Block.getBlockFromItem(MaterialRegistry.getMaterialFromName("Copper").getProduct(AllowedProducts.getProductByName("COIL")).getItem()), MaterialRegistry.getMaterialFromName("Copper").getMeta()), AdvancedRocketryBlocks.blockMotor, LibVulpesBlocks.blockStructureBlock, 'O'}}
	};


	@Override
	public Object[][][] getStructure() {
		return structure;
	}

	@Override
	public List<ModuleBase> getModules(int ID, EntityPlayer player) {
		List<ModuleBase> modules = super.getModules(ID, player);

		modules.add(new ModuleProgress(70, 20, 0, TextureResources.rollingMachineProgressBar, this));
		return modules;
	}

	@Override
	public boolean canProcessRecipe(IRecipe recipe) {
		if(!fluidInPorts.isEmpty()) {
			IFluidHandler fluidHandler = fluidInPorts.get(0);
			FluidStack fluid;
			if(fluidHandler == null || (fluid = fluidHandler.drain(new FluidStack(FluidRegistry.WATER, 100), false)) == null 
					|| fluid.amount != 100)
				return false;
		}

		return super.canProcessRecipe(recipe);
	}


	@Override
	public void consumeItems(IRecipe recipe) {
		super.consumeItems(recipe);
		IFluidHandler fluidHandler = fluidInPorts.get(0);
		fluidHandler.drain(new FluidStack(FluidRegistry.WATER, 100), true);
	}

	@Override
	public SoundEvent getSound() {
		return AudioRegistry.rollingMachine;
	}

	@Override
	public int getSoundDuration() {
		return 30;
	}
	
	@Override
	public boolean shouldHideBlock(World world, BlockPos pos, IBlockState tile) {
		return tile != Block.getBlockFromItem(MaterialRegistry.getMaterialFromName("Copper").getProduct(AllowedProducts.getProductByName("COIL")).getItem());
	}

	@Override
	public String getMachineName() {
		return "tile.rollingMachine.name";
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		return new AxisAlignedBB(pos.add(-4,-4,-4), pos.add(4,4,4));
	}

}
