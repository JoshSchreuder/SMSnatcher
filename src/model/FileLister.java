/* 
Copyright (C) 2011 Josh Schreuder
This file is part of SMSnatcher.

SMSnatcher is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

SMSnatcher is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with SMSnatcher.  If not, see <http://www.gnu.org/licenses/>. 
*/
package model;

import java.util.*;
import java.io.*;

/**
* Recursive file listing under a specified directory.
*  
* @author javapractices.com
* @author Alex Wong
* @author anonymous user
*/
public class FileLister {
	  /**
	  * Recursively walk a directory tree and return a List of all
	  * Files found; the List is sorted using File.compareTo().
	  *
	  * @param aStartingDir is a valid directory, which can be read.
	  */
	  static public List<File> getFileListing(
	    File aStartingDir
	  ) throws FileNotFoundException {
		System.out.println("Validating directory");
	    validateDirectory(aStartingDir);
	    System.out.println("Getting file list");
	    List<File> result = getFileListingNoSort(aStartingDir);
	    System.out.println("Sorting files");
	    Collections.sort(result);
	    return result;
	  }

	  // PRIVATE //
	  static private List<File> getFileListingNoSort(
	    File aStartingDir
	  ) throws FileNotFoundException {
	    List<File> result = new ArrayList<File>();
	    File[] filesAndDirs = aStartingDir.listFiles();
	    List<File> filesDirs = Arrays.asList(filesAndDirs);
	    for(File file : filesDirs) {
	      if ( ! file.isFile() ) {
	        //must be a directory
	        //recursive call!
	    	System.out.println(file.getName());
	        List<File> deeperList = getFileListingNoSort(file);
	        result.addAll(deeperList);
	      }
	      else {
	    	  if(file.getName().endsWith("mp3")) {
	    		System.out.println(file.getName());
	    		result.add(file);
	    	  }
	      }
	    }
	    return result;
	  }

	  /**
	  * Directory is valid if it exists, does not represent a file, and can be read.
	  */
	  static private void validateDirectory (
	    File aDirectory
	  ) throws FileNotFoundException {
	    if (aDirectory == null) {
	      throw new IllegalArgumentException("Directory should not be null.");
	    }
	    if (!aDirectory.exists()) {
	      throw new FileNotFoundException("Directory does not exist: " + aDirectory);
	    }
	    if (!aDirectory.isDirectory()) {
	      throw new IllegalArgumentException("Is not a directory: " + aDirectory);
	    }
	    if (!aDirectory.canRead()) {
	      throw new IllegalArgumentException("Directory cannot be read: " + aDirectory);
	    }
	  }
} 
