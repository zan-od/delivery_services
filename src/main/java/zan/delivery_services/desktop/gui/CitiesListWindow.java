package zan.delivery_services.desktop.gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import zan.delivery_services.city.bo.CityBo;
import zan.delivery_services.city.model.City;
import zan.delivery_services.delivery_service.bo.DeliveryServiceBo;
import zan.delivery_services.delivery_service.model.DeliveryService;

public class CitiesListWindow extends JInternalFrame {
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
	public CitiesListWindow() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		this.closable = true;
		this.maximizable = true;
		this.resizable = true;
		
		JToolBar toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		
		CityBo cityBo = (CityBo) applicationContext.getBean("cityBo");
		List<City> cities = cityBo.getAllCities();
		
		table = new JTable();
		table.setModel(new CitiesTableModel(cities));
		getContentPane().add(table, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane(table);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

	}
	
	public class CitiesTableModel implements TableModel {
		 
        //private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
 
        private List<City> beans;
 
        public CitiesTableModel(List<City> beans) {
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
            return 4;
        }
 
        public String getColumnName(int columnIndex) {
            switch (columnIndex) {
            case 0:
                return "Код";
            case 1:
                return "Перевозчик";
            case 2:
                return "Имя";
            case 3:
                return "Ссылка";
            }
            return "";
        }
 
        public int getRowCount() {
            return beans.size();
        }
 
        public Object getValueAt(int rowIndex, int columnIndex) {
        	City bean = beans.get(rowIndex);
            switch (columnIndex) {
            case 0:
                return bean.getId();
            case 1:
                /*DeliveryService service = bean.getService();
                if (service != null)
                	return service.getName();
                else*/
                	return "<none>";
            case 2:
                return bean.getName();
            case 3:
                return bean.getRef();
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