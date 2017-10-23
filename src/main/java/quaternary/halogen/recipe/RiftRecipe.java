package quaternary.halogen.recipe;

import com.google.common.base.Preconditions;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import quaternary.halogen.util.Utils;

import java.util.Optional;

/** Styled a bit after Botania's RecipeManaInfusion */
public class RiftRecipe {
	private final Object in;
	private final ItemStack out;
	
	public RiftRecipe(String oreKey, ItemStack outStack) {
		//Thanks nutty boi for showing me preconditions xd
		Utils.checkItemStack(outStack, "Recipe output");
		
		in = oreKey;
		out = outStack;
	}
	
	public RiftRecipe(ItemStack inStack, ItemStack outStack) {
		Utils.checkItemStack(inStack, "Recipe input");
		Utils.checkItemStack(outStack, "Recipe output");
		
		in = inStack;
		out = outStack;
	}
	
	/** Convenience */
	public RiftRecipe(Item in_, Item out_) {
		this(new ItemStack(in_), new ItemStack(out_));
	}
	
	public Optional<ItemStack> getOutput(ItemStack thingToMatch) {
		if(in instanceof ItemStack) {
			if(((ItemStack) in).isItemEqual(thingToMatch)) return Optional.of(out);
		} else {
			NonNullList<ItemStack> matchingInputs = OreDictionary.getOres((String) in);
			
			//TODO: Am I doing this right?
			for(ItemStack s : matchingInputs) {
				if(s.isItemEqual(thingToMatch)) return Optional.of(out);
			}
		}
		
		return Optional.empty();
	}
	
}