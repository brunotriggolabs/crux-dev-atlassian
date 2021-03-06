/*
 * Copyright 2011 cruxframework.org.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.cruxframework.crux.core.client.datasource;

import java.util.ArrayList;
import java.util.List;

import org.cruxframework.crux.core.client.collection.FastMap;


/**
 * 
 * @author Thiago da Rosa de Bustamante
 *
 */
public class ColumnDefinitions<E>
{
	private List<String> columnNames = new ArrayList<String>();;
	private FastMap<ColumnDefinition<?,E>> columns = new FastMap<ColumnDefinition<?,E>>();;
	
	public void addColumn(ColumnDefinition<?,E> columnMetadata)
	{
		if (!columnNames.contains(columnMetadata.getName()))
		{
			columnNames.add(columnMetadata.getName());
			columns.put(columnMetadata.getName(), columnMetadata);
		}
	}
	
	public List<String> getColumnNames()
	{
		return columnNames;
	}
	
	public ColumnDefinition<?,E> getColumn(String columnName)
	{
		return columns.get(columnName);
	}
	
	public int getColumnPosition(String columnName)
	{
		return columnNames.indexOf(columnName);
	}
	
	public void clear()
	{
		columnNames.clear();
		columns.clear();
	}
}
