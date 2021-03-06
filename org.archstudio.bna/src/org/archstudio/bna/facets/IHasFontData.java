package org.archstudio.bna.facets;

import org.archstudio.bna.keys.IThingKey;
import org.archstudio.bna.keys.ThingKey;
import org.eclipse.jdt.annotation.NonNullByDefault;

/*
 * DO NOT EDIT THIS FILE, it is automatically generated. ANY MODIFICATIONS WILL BE OVERWRITTEN. To modify, update the
 * thingdefinition extension at org.archstudio.bna/Package[name=org.archstudio.bna.facets]/Facet[name=FontData].
 */

@SuppressWarnings("all")
@NonNullByDefault
public interface IHasFontData extends org.archstudio.bna.IThing {
	public static final IThingKey<java.lang.Boolean> DONT_INCREASE_FONT_SIZE_KEY =
			ThingKey.create(com.google.common.collect.Lists.newArrayList("dontIncreaseFontSize", IHasFontData.class));

	public static final IThingKey<java.lang.String> FONT_NAME_KEY =
			ThingKey.create(com.google.common.collect.Lists.newArrayList("fontName", IHasFontData.class));

	public static final IThingKey<java.lang.Integer> FONT_SIZE_KEY =
			ThingKey.create(com.google.common.collect.Lists.newArrayList("fontSize", IHasFontData.class));

	public static final IThingKey<org.archstudio.swtutils.constants.FontStyle> FONT_STYLE_KEY =
			ThingKey.create(com.google.common.collect.Lists.newArrayList("fontStyle", IHasFontData.class));

	public boolean isDontIncreaseFontSize();

	public java.lang.String getFontName();

	public int getFontSize();

	public org.archstudio.swtutils.constants.FontStyle getFontStyle();
}