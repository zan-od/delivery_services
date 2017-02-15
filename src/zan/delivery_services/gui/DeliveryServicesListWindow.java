package zan.delivery_services.gui;

import java.awt.EventQueue;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import javax.swing.JToolBar;
import javax.swing.event.TableModelListener;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import zan.delivery_services.DeliveryService;
import zan.delivery_services.db.*;

public class DeliveryServicesListWindow extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public DeliveryServicesListWindow() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		this.closable = true;
		this.maximizable = true;
		this.resizable = true;
		
		JToolBar toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		table = new JTable();
//		table.setModel(new DefaultTableModel(
//			new Object[][] {
//				{"NEW_POST", "New Post"},
//				{"INTIME", "Intime"},
//			},
//			new String[] {
//				"Id", "Name"
//			}
//		) {
//			/**
//			 * 
//			 */
//			private static final long serialVersionUID = 1L;
//			Class[] columnTypes = new Class[] {
//				String.class, String.class
//			};
//			public Class getColumnClass(int columnIndex) {
//				return columnTypes[columnIndex];
//			}
//		});
		
		List<DeliveryService> services= Arrays.asList();
		try {
			services = DeliveryService.getAll();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		table.setModel(new DeliveryServicesTableModel(services));
		getContentPane().add(table, BorderLayout.CENTER);

	}
	
	public class DeliveryServicesTableModel implements TableModel {
		 
        //private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
 
        private List<DeliveryService> beans;
 
        public DeliveryServicesTableModel(List<DeliveryService> beans) {
            this.beans = beans;
        }
 
        public void addTableModelListener(TableModelListener listener) {
            //listeners.add(listener);
        }
        
        public void removeTableModelListener(TableModelListener listener) {
            //listeners.remove(listener);
        }
 
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }
 
        public int getColumnCount() {
            return 3;
        }
 
        public String getColumnName(int columnIndex) {
            switch (columnIndex) {
            case 0:
                return "Код";
            case 1:
                return "Имя";
            case 2:
                return "Ключ";
            }
            return "";
        }
 
        public int getRowCount() {
            return beans.size();
        }
 
        public Object getValueAt(int rowIndex, int columnIndex) {
        	DeliveryService bean = beans.get(rowIndex);
            switch (columnIndex) {
            case 0:
                return bean.getCode();
            case 1:
                return bean.getName();
            case 2:
                return bean.getApiKey();
            }
            return "";
        }
 
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
 
        public void setValueAt(Object value, int rowIndex, int columnIndex) {
 
        }
 
    }

}
