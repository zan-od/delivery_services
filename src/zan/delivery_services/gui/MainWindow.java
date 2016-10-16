package zan.delivery_services.gui;

import java.awt.EventQueue;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import zan.delivery_services.DeliveryService;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Reader;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JToggleButton;

public class MainWindow {

	private JFrame frame;
	
	private final JDesktopPane desktopPane = new JDesktopPane();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//test();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	static void test(){
		try{
			System.out.println("start");
			DeliveryService service = new DeliveryService();
			service.setCode("INTIME");
			service.setName("Intime");
			service.insert();
			System.out.println("end");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JMenuBar menuBar = new JMenuBar();
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);
		
		JMenu mnReferences = new JMenu("References");
		menuBar.add(mnReferences);
		
		JMenuItem mntmDeliveryServices = new JMenuItem("Delivery Services");
		mntmDeliveryServices.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DeliveryServicesListWindow window = new DeliveryServicesListWindow();
				window.show();
				desktopPane.add(window);
			}
		});
		mnReferences.add(mntmDeliveryServices);
		
		JMenuItem mntmCities = new JMenuItem("Cities");
		mntmCities.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CitiesListWindow window = new CitiesListWindow();
				window.show();
				desktopPane.add(window);
			}
		});
		mnReferences.add(mntmCities);
		
		JMenu mnLoadOffices = new JMenu("Load offices");
		menuBar.add(mnLoadOffices);
		
		JMenuItem mntmLoadOfficeNewPost = new JMenuItem("Load offices of New Post");
		mntmLoadOfficeNewPost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DeliveryService service = new DeliveryService();
				service.connect();
			}
		});
		mnLoadOffices.add(mntmLoadOfficeNewPost);
		
		this.frame.getContentPane().add(desktopPane, BorderLayout.CENTER);
		
		JToolBar toolBar = new JToolBar();
		frame.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		JToggleButton tglbtnTest = new JToggleButton("test");
		toolBar.add(tglbtnTest);
	}

}
