/*
 * Copyright (c) 2010 Tom Parker <thpr@users.sourceforge.net>
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA
 */
package plugin.lsttokens.gamemode.tab;

import java.io.File;

import pcgen.cdom.content.TabInfo;
import pcgen.core.SettingsHandler;
import pcgen.core.utils.CoreUtility;
import pcgen.rules.context.LoadContext;
import pcgen.rules.persistence.token.CDOMPrimaryToken;
import pcgen.rules.persistence.token.ParseResult;

/**
 * Class deals with CONTEXT Token
 */
public class ContextToken implements CDOMPrimaryToken<TabInfo>
{

	public String getTokenName()
	{
		return "CONTEXT";
	}

	public ParseResult parseToken(LoadContext context, TabInfo ti, String value)
	{
		if ((value == null) || (value.length() == 0))
		{
			// This is OK
			return ParseResult.SUCCESS;
		}

		String helpPath = CoreUtility.fixFilenamePath(SettingsHandler
				.getPcgenDocsDir()
				+ File.separator + value);
		File helpFile = new File(helpPath);
		if (!helpFile.exists())
		{
			return new ParseResult.Fail("Missing Documentation: "
					+ helpFile.getAbsolutePath());
		}
		ti.setHelpContext(helpFile);
		ti.setRawHelpContext(value);
		return ParseResult.SUCCESS;
	}

	public String[] unparse(LoadContext context, TabInfo ti)
	{
		String hc = ti.getRawHelpContext();
		if (hc == null)
		{
			return null;
		}
		return new String[] { hc };
	}

	public Class<TabInfo> getTokenClass()
	{
		return TabInfo.class;
	}
}
