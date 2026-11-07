package net.mcreator.lucidnight.mixin;

import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.core.Holder;

import net.mcreator.lucidnight.init.LucidnightModBiomes;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;

@Mixin(NoiseGeneratorSettings.class)
public class NoiseGeneratorSettingsMixin implements LucidnightModBiomes.LucidnightModNoiseGeneratorSettings {
	@Unique
	private Holder<DimensionType> lucidnight_dimensionTypeReference;

	@WrapMethod(method = "surfaceRule")
	public SurfaceRules.RuleSource surfaceRule(Operation<SurfaceRules.RuleSource> original) {
		SurfaceRules.RuleSource retval = original.call();
		if (this.lucidnight_dimensionTypeReference != null) {
			retval = LucidnightModBiomes.adaptSurfaceRule(retval, this.lucidnight_dimensionTypeReference);
		}
		return retval;
	}

	@Override
	public void setlucidnightDimensionTypeReference(Holder<DimensionType> dimensionType) {
		this.lucidnight_dimensionTypeReference = dimensionType;
	}
}