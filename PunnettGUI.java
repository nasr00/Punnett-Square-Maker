package PunnettSquares;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class PunnettGUI {
	private JFrame inframe;
	private JTextField in1 = new JTextField(10);
	private JTextField in2 = new JTextField(10);
	private JLabel prompt1 = new JLabel("Parent 1 Gene(s):");
	private JLabel prompt2 = new JLabel("Parent 2 Gene(s):");
	private JButton generate;
	private JLabel mismatch = new JLabel("Genes don't match! Try again.");
	private JLabel empty = new JLabel("Please input some genes first.");
	private JLabel format = new JLabel("Genes aren't unique/formatted correctly.");
	private JFrame outframe;
	private JTable table;
	private JScrollPane tablePanel;
	private JScrollPane scrollCombos;
	private JPanel combosPanel;
	private JTextField searchBar;
	private JButton searchButton;
	private JPanel searchPanel;
	private JLabel none;
	public static void main(String[] args) {
		PunnettGUI test = new PunnettGUI();
	}
	
	public PunnettGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                create();
            }
        });
    }
	public void create() {
		inframe = new JFrame("Punnett Square Calculator");  
        inframe.setSize(250, 150);
        inframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inframe.setLocationRelativeTo(null);
        inframe.setVisible(true);
        inframe.setResizable(false);
        final JPanel inPanel = new JPanel();
        inPanel.add(prompt1);
        inPanel.add(in1);
        inPanel.add(prompt2);
        inPanel.add(in2);
        generate = new JButton("Generate!");
        inPanel.add(generate);
        inPanel.add(mismatch);
        inPanel.add(empty);
        inPanel.add(format);
        format.setVisible(false);
        empty.setVisible(false);
        mismatch.setVisible(false);
        inframe.add(inPanel, BorderLayout.CENTER);
        generate.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(!in1.getText().toLowerCase().equals(in2.getText().toLowerCase())) {
        			mismatch.setVisible(true);
        			empty.setVisible(false);
        			format.setVisible(false);
        		} else if(in1.getText().equals("")){
        			empty.setVisible(true);
        			mismatch.setVisible(false);
        			format.setVisible(false);
        		} else {
        			boolean formatting = true;
        			if(in1.getText().length() % 2 != 0) {formatting = false; } else {
        				for(int i = 0; i < in1.getText().length() - 1; i+=2) {
        					if(Character.toLowerCase(in1.getText().charAt(i+1)) != Character.toLowerCase(in1.getText().charAt(i)) || in1.getText().substring(0,i).contains(in1.getText().substring(i, i+1))) {
        						formatting = false;
        					}
        				}
        				if(!in1.getText().matches("[a-zA-Z]+")) {
        					formatting = false;
        				}
        			}
        			if(formatting == true) {
        				inframe.setVisible(false);
        				outframe = new JFrame("Punnett Square Calculator");  
        		        //outframe.setSize(1200, 800);
        		        outframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        		        outframe.setLocationRelativeTo(null);
        		        outframe.setResizable(true);
        		        Punnettnew result = new Punnettnew(in1.getText(), in2.getText());
        		        String[][] square = result.getSquare();

        		        table = new JTable(square.length-2, square[0].length-1);
        		        for(int i = 2; i < square.length; i++) {
        					for(int j = 2; j < square[i].length; j++) {
        						table.setValueAt(square[i][j], i-2, j-1);
        					}
        				}
        		        table.getColumnModel().getColumn(0).setHeaderValue("");
        		        for(int i = 2; i < square.length; i++) {
        		        	table.getColumnModel().getColumn(i-1).setHeaderValue(square[0][i]);
        		        	table.setValueAt(square[i][0], i-2, 0);
        		        }
        		        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  
        		        table.setDefaultEditor(Object.class, null);
        		        table.setRowHeight(40);
        		        TableColumn firstCol = table.getColumnModel().getColumn(0);
        		        firstCol.setCellRenderer(new ColumnColorRenderer(new Color(238, 238, 238), Color.black));
        		        tablePanel = new JScrollPane(table);		        
        		        outframe.add(tablePanel, BorderLayout.CENTER);             		      
        		        
        		        ArrayList<String> combos = result.getProbs();
        		        ArrayList<JLabel> comboLabels = new ArrayList<JLabel>();
        		        combosPanel = new JPanel();
        		        combosPanel.setLayout(new BoxLayout(combosPanel, BoxLayout.Y_AXIS));
        		        JLabel numCombos = new JLabel(combos.get(0));
        		        numCombos.setVisible(true);
        		        combosPanel.add(numCombos);    	
        		        combosPanel.add(new JLabel("  "));
        		        scrollCombos = new JScrollPane(combosPanel);
        		        scrollCombos.setVisible(true);
        		        for(int i = 1; i < combos.size(); i++) {
        		        	JLabel temp = new JLabel(combos.get(i));
        		        	comboLabels.add(temp);
        		        	combosPanel.add(temp);
        		        }
        		        none = new JLabel("No search results found.");
        		        none.setVisible(false);
        		        combosPanel.add(none);
        		        outframe.add(scrollCombos, BorderLayout.EAST);
        		        
        		        
        		        searchPanel = new JPanel();
        		        searchBar = new JTextField(40);
        		        searchButton = new JButton("Search");
        		        searchButton.addActionListener(new ActionListener() {
        		        	public void actionPerformed(ActionEvent e) {
        		        		int counter = 0;
        		        		for(int i = 0; i < comboLabels.size(); i++) {
        		        			if(comboLabels.get(i).getText().contains(searchBar.getText())) {
        		        				comboLabels.get(i).setVisible(true);
        		        			} else {
        		        				comboLabels.get(i).setVisible(false);
        		        				counter++;
        		        			}
        		        			if(counter == comboLabels.size()) {
        		        				none.setVisible(true);
        		        			} else {
        		        				none.setVisible(false);
        		        			}
        		        		}
        		        	}
        		        });
        		        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        		        searchPanel.add(new JLabel("Search Trait(s) or % Here: "));
        		        searchPanel.add(searchBar);
        		        searchPanel.add(searchButton);
        		        outframe.add(searchPanel, BorderLayout.SOUTH);
        		        
        		        JPanel parents = new JPanel();
        		        JLabel parentsLabel = new JLabel("Parent 1 Alleles: " + in1.getText() + ", Parent 2 Alleles: " + in2.getText());
        		        parents.add(parentsLabel, BorderLayout.CENTER);
        		        outframe.add(parents, BorderLayout.NORTH);
        		        
        		        int w = table.getRowHeight();    		        
        		        outframe.setSize((int)Math.min(w*table.getRowCount() + 650, 1200), (int)Math.min(w*1.5*table.getRowCount() + 250, 800));
        		        //outframe.pack();
        		        outframe.setLocationRelativeTo(null);
        		        outframe.setVisible(true);
        				
        			} else {
        				format.setVisible(true);
            			empty.setVisible(false);
            			mismatch.setVisible(false);
        			}
        		}
        	}
        });
	}
	class ColumnColorRenderer extends DefaultTableCellRenderer { //https://www.tutorialspoint.com/how-can-we-set-the-background-foreground-color-for-individual-column-of-a-jtable-in-java
		   Color backgroundColor, foregroundColor;
		   public ColumnColorRenderer(Color backgroundColor, Color foregroundColor) {
		      super();
		      this.backgroundColor = backgroundColor;
		      this.foregroundColor = foregroundColor;
		   }
		   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,   boolean hasFocus, int row, int column) {
		      Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		      cell.setBackground(backgroundColor);
		      cell.setForeground(foregroundColor);
		      return cell;
		   }
	}
	
}
