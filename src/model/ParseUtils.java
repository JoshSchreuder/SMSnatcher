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

import org.jsoup.nodes.Node;

public class ParseUtils {
	
	// Lovingly borrowed from https://gist.github.com/491407
	public static void removeComments(Node node) {
	    // as we are removing child nodes while iterating, we cannot use a normal foreach over children,
	    // or will get a concurrent list modification error.
	    int i = 0;
	    while (i < node.childNodes().size()) {
	        Node child = node.childNode(i);
	        if (child.nodeName().equals("#comment"))
	            child.remove();
	        else {
	            removeComments(child);
	            i++;
	        }
	    }
	}
}
