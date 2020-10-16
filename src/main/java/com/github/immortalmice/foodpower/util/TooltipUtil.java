package com.github.immortalmice.foodpower.util;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;

public class TooltipUtil{
	private List<ITextComponent> tooltip = null;
	private static String leftSpace = "  ";

	public TooltipUtil(List<ITextComponent> tooltipIn){
		this.tooltip = tooltipIn;
	}

	public void add(String str){
		this.tooltip.add(new StringTextComponent(str));
	}

	public void add(String str, Style style){
		this.tooltip.add((new StringTextComponent(str)).setStyle(style));
	}

	public void add(ITextComponent text){
		this.tooltip.add(text);
	}

	public void addWithLeftSpace(String str){
		this.tooltip.add(new StringTextComponent(leftSpace + str));
	}

	public void addTranslate(String key, Object... parameters){
		this.add(TooltipUtil.translate(key, parameters));
	}

	public void addTranslate(String key, Style style, Object... parameters){
		this.add(TooltipUtil.translate(key, parameters), style);
	}

    public void newBlankRow(){
        this.tooltip.add(new StringTextComponent(""));
    }

	public static String translate(String key, Object... parameters){
		return I18n.format(key, parameters);
	}
}
