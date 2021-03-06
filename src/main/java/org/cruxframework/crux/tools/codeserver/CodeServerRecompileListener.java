/*
 * Copyright 2014 cruxframework.org.
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
package org.cruxframework.crux.tools.codeserver;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cruxframework.crux.core.utils.FileUtils;

import com.google.gwt.dev.codeserver.CompileDir;
import com.google.gwt.dev.codeserver.RecompileListener;

/**
 * 
 * @author Thiago da Rosa de Bustamante
 *
 */
public class CodeServerRecompileListener implements RecompileListener 
{
	private static final Log logger = LogFactory.getLog(CodeServerRecompileListener.class);

	private Map<String, CompileDir> compileDir = new HashMap<String, CompileDir>();
	private File webDir;
	private CompilationCallback compilationCallback;

	public CodeServerRecompileListener(String webDir) 
	{
		if (webDir != null && webDir.length() > 0)
		{
			this.webDir = new File(webDir);
		}
	}
	
	@Override
	public void startedCompile(String moduleName, int compileId, CompileDir compileDir) 
	{
		this.compileDir.put(moduleName, compileDir);
		if (compilationCallback != null)
		{
			compilationCallback.onCompilationStart(moduleName);
		}
	}
	
	@Override
	public void finishedCompile(String moduleName, int compileId, boolean success) 
	{
		updateWebDir(moduleName, success);
		if (compilationCallback != null)
		{
			compilationCallback.onCompilationEnd(moduleName, success);
		}
	}

	public void setCompilationCallback(CompilationCallback compilationCallback)
	{
		this.compilationCallback = compilationCallback;
	}
	
	private void updateWebDir(String moduleName, boolean success) 
	{
		if (webDir != null)
		{
			if (success)
			{
				CompileDir dir = compileDir.get(moduleName);
				try 
				{
					FileUtils.copyFilesFromDir(dir.getWarDir(), webDir);
				} 
				catch (IOException e) 
				{
					logger.error("Error updating webDir", e);
				}
			}
		}
	}
	
	public static interface CompilationCallback
	{
		void onCompilationStart(String moduleName);
		void onCompilationEnd(String moduleName, boolean success);
	}
}