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

import java.util.Arrays;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class SongTableModel extends AbstractTableModel {
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		Vector<String> columns = new Vector<String>(Arrays.asList("Artist","Track Name","Lyrics","File"));
		
		public String getColumnName(int col) {
			return columns.get(col).toString();
		}
		public int getRowCount() { return data.size(); }
		public int getColumnCount() { return columns.size(); }
		public String getValueAt(int row, int col) {
			return data.get(row).get(col);
		}
		public boolean isCellEditable(int row, int col)
		{ return false; }
		
		public void addRow(Vector<String> row) {
			data.add(row);
			for(int col = 0 ; col < columns.size() ; col++)
				fireTableCellUpdated(data.size()-1, col);
		}
		
		public void setValueAt(Object value, int row, int col) {
			data.get(row).set(col, (String)value);
			fireTableCellUpdated(row, col);
		}
		
		public void clearTable() {
			data.clear();
			this.fireTableDataChanged();
		}
}
