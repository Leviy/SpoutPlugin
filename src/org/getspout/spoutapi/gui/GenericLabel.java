/*
 * This file is part of Spout API (http://wiki.getspout.org/).
 * 
 * Spout API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Spout API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.getspout.spoutapi.gui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.getspout.spoutapi.packet.PacketUtil;

public class GenericLabel extends GenericWidget implements Label{
	protected String text = "";
	protected WidgetAnchor align = WidgetAnchor.TOP_LEFT;
	protected Color color = new Color(1F, 1F, 1F);
	protected boolean auto = true;
	protected boolean resize = false;
	protected int textWidth = -1, textHeight = -1;
	public GenericLabel(){
		
	}

	public GenericLabel(String text) {
		this.text = text;
	}
	
	@Override
	public WidgetType getType() {
		return WidgetType.Label;
	}
	
	@Override
	public int getNumBytes() {
		return super.getNumBytes() + PacketUtil.getNumBytes(getText()) + 18;
	}
	
	@Override
	public int getVersion() {
		return super.getVersion() + 3;
	}
	
	@Override
	public void readData(DataInputStream input) throws IOException {
		super.readData(input);
		this.setText(PacketUtil.readString(input));
		this.setAlign(WidgetAnchor.getAnchorFromId(input.readByte()));
		this.setAuto(input.readBoolean());
		this.setTextColor(PacketUtil.readColor(input));
	}

	@Override
	public void writeData(DataOutputStream output) throws IOException {
		super.writeData(output);
		PacketUtil.writeString(output, getText());
		output.writeByte(align.getId());
		output.writeBoolean(isAuto());
		PacketUtil.writeColor(output, getTextColor());
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public Label setText(String text) {
		this.text = text;
		textHeight = textWidth = -1;
		doResize();
		return this;
	}
	
	@Override
	@Deprecated
	public boolean getAuto() {
		return auto;
	}
	
	@Override
	public boolean isAuto() {
		return auto;
	}
	
	@Override
	public Label setAuto(boolean auto) {
		this.auto = auto;
		return this;
	}

	@Override
	public WidgetAnchor getAlign() {
		return align;
	}

	@Override
	public Label setAlign(WidgetAnchor pos) {
		this.align = pos;
		return this;
	}

	@Override
	public Color getTextColor() {
		return color;
	}

	@Override
	public Label setTextColor(Color color) {
		this.color = color;
		return this;
	}

	@Override
	@Deprecated
	public Align getAlignX() {
		switch (align) {
			case TOP_CENTER:
			case CENTER_CENTER:
			case BOTTOM_CENTER:
				return Align.SECOND;
			case TOP_RIGHT:
			case CENTER_RIGHT:
			case BOTTOM_RIGHT:
				return Align.THIRD;
			case TOP_LEFT:
			case CENTER_LEFT:
			case BOTTOM_LEFT:
			default:
				return Align.FIRST;
		}
	}

	@Override
	@Deprecated
	public Widget setAlignX(Align pos) {
		return this;
	}

	@Override
	@Deprecated
	public Align getAlignY() {
		switch (align) {
			case CENTER_LEFT:
			case CENTER_CENTER:
			case CENTER_RIGHT:
				return Align.SECOND;
			case BOTTOM_LEFT:
			case BOTTOM_CENTER:
			case BOTTOM_RIGHT:
				return Align.THIRD;
			case TOP_LEFT:
			case TOP_CENTER:
			case TOP_RIGHT:
			default:
				return Align.FIRST;
		}
	}

	@Override
	@Deprecated
	public Widget setAlignY(Align pos) {
		return this;
	}
	
	@Override
	public Label copy() {
		return ((Label)super.copy()).setText(getText()).setAuto(isAuto()).setTextColor(getTextColor()).setResize(isResize());
	}


	@Override
	public boolean isResize() {
		return resize;
	}

	@Override
	public Label setResize(boolean resize) {
		this.resize = resize;
		doResize();
		return this;
	}
	
	@Override
	public Label doResize() {
		if (resize) {
			if (textHeight < 0 || textWidth < 0) {
				textHeight = getStringHeight(text);
				textWidth = getStringWidth(text);
			}
			if (super.isFixed()) {
				super.setHeight(textHeight);
				super.setWidth(textWidth);
			} else {
				super.setMinHeight(textHeight);
				super.setMinWidth(textWidth);
			}
		} else {
			textHeight = textWidth = -1;
		}
		return this;
	}

	@Override
	public Widget setFixed(boolean fixed) {
		super.setFixed(fixed);
		doResize();
		return this;
	}

	/**
	 * Super secret method to get the height of a string...
	 * @param text
	 * @return 
	 */
	public static int getStringHeight(String text) {
		return text.split("\n").length * 10;
	}

	/**
	 * Super secret method to get the width of a string...
	 * @param text
	 * @return 
	 */
	public static int getStringWidth(String text) {
		final int[] characterWidths = new int[]{
			1, 9, 9, 8, 8, 8, 8, 7, 9, 8, 9, 9, 8, 9, 9, 9,
			8, 8, 8, 8, 9, 9, 8, 9, 8, 8, 8, 8, 8, 9, 9, 9,
			4, 2, 5, 6, 6, 6, 6, 3, 5, 5, 5, 6, 2, 6, 2, 6,
			6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 2, 2, 5, 6, 5, 6,
			7, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 6, 6, 6, 6, 6,
			6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 4, 6, 6,
			3, 6, 6, 6, 6, 6, 5, 6, 6, 2, 6, 5, 3, 6, 6, 6,
			6, 6, 6, 6, 4, 6, 6, 6, 6, 6, 6, 5, 2, 5, 7, 6,
			6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 3, 6, 6,
			6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6,
			6, 3, 6, 6, 6, 6, 6, 6, 6, 7, 6, 6, 6, 2, 6, 6,
			8, 9, 9, 6, 6, 6, 8, 8, 6, 8, 8, 8, 8, 8, 6, 6,
			9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9,
			9, 9, 9, 9, 9, 9, 9, 9, 9, 6, 9, 9, 9, 5, 9, 9,
			8, 7, 7, 8, 7, 8, 8, 8, 7, 8, 8, 7, 9, 9, 6, 7,
			7, 7, 7, 7, 9, 6, 7, 8, 7, 6, 6, 9, 7, 6, 7, 1
		};
		final String allowedCharacters = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_'abcdefghijklmnopqrstuvwxyz{|}~?Ã³ÚÔõÓÕþÛÙÞ´¯ý─┼╔µã¶÷‗¹¨ Í▄°úÏÎâßÝ¾·±Ð¬║┐«¼¢╝í½╗";
		int length = 0;
		for (String line : ChatColor.stripColor(text).split("\n")) {
			int lineLength = 0;
			boolean skip = false;
			for (char ch : line.toCharArray()) {
				if (skip) {
					skip = false;
				} else if (ch == '\u00A7') {
					skip = true;
				} else if (allowedCharacters.indexOf(ch) != -1) {
					lineLength += characterWidths[ch];
				}
			}
			length = Math.max(length, lineLength);
		}
		return length;
	}
}
