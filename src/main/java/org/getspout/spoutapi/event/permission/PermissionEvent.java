/*
 * This file is part of SpoutPlugin.
 *
 * Copyright (c) 2011-2012, SpoutDev <http://www.spout.org/>
 * SpoutPlugin is licensed under the GNU Lesser General Public License.
 *
 * SpoutPlugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SpoutPlugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.getspout.spoutapi.event.permission;

public interface PermissionEvent {
	/**
	 * Gets the default result of this event, if unmodified.
	 * @return default result
	 */
	public boolean getDefaultResult();

	/**
	 * Gets the result of this event.
	 * <p/>
	 * If true, the action will be allowed
	 * @return result
	 */
	public boolean getResult();

	/**
	 * Sets the result of this event.
	 * @param result to set
	 */
	public void setResult(boolean result);
}