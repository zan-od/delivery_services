package zan.delivery_services.desktop.gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import zan.delivery_services.delivery_service.bo.DeliveryServiceBo;
import zan.delivery_services.delivery_service.model.DeliveryService;

public class DeliveryServicesListWindow extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;

	@Autowired
	private ApplicationContext applicationContext;
	
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
		
		applicationContext = new ClassPathXmlApplicationContext("BeanLocations.xml");
		DeliveryServiceBo deliveryServicesBo = (DeliveryServiceBo) applicationContext.getBean("deliveryServiceBo");
		List<DeliveryService> services= deliveryServicesBo.getAllServices();
		
		table = new JTable();
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
                return "���";
            case 1:
                return "���";
            case 2:
                return "����";
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