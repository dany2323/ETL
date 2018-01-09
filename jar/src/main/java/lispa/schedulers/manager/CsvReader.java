package lispa.schedulers.manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

public class CsvReader {
	
	private LinkedList<String[]> csvFile = new LinkedList<String[]>();
	private String[] columnNames = null;
	
	public int getRowCount()
	{
		return this.csvFile.size();
	}
	
	public int getColumnCount()
	{
		return columnNames == null ? 0 : columnNames.length;
	}
	
	public String[] getColumnNames()
	{
		return columnNames;
	}
	
	public String getCell(int row, String columnName)
	{
		if(row < 0 || row >= getRowCount())
			throw new IllegalArgumentException();
		
		if(columnName == null || columnName.trim().length() == 0)
			throw new IllegalArgumentException();
		
		columnName = columnName.toLowerCase();
		
		return csvFile.get(row)[Arrays.asList(columnNames).indexOf(columnName)];
	}
	
	public int FindRowByCell(String columnName, String value)
	{
		if(columnName == null || columnName.trim().length() == 0)
			throw new IllegalArgumentException();
		
		for(int i = 0; i < getRowCount(); i++)
		{
			if(this.getCell(i, columnName) == value)
				return i;
		}
		
		return -1;
	}
	
	public void RemoveRow(int rowIndex)
	{
		this.csvFile.remove(rowIndex);
	}
	
	public void ReadFile(File csvFileName)
	{
		BufferedReader br = null;
		String line;
		String csvSplitBy = ";";
		
		try
		{
			br = new BufferedReader(new FileReader(csvFileName));
			line = br.readLine();
			
			if(line != null)
			{
				columnNames = line.split(csvSplitBy, -1);
				for(int i = 0; i < columnNames.length; i++)
					columnNames[i] = columnNames[i].trim().toLowerCase();
			}
			
	        while ((line = br.readLine()) != null) {
	
	            // use comma as separator
	            String[] cells = line.split(csvSplitBy, -1);
	            for(int i = 0; i < cells.length; i++)
	            	cells[i] = cells[i].trim();
	            csvFile.add(cells);
	        }
		}
		catch(Exception exc)
		{
			exc.printStackTrace();
		}
		finally
		{
			if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
		}
	}
}
