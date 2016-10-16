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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Console;
import java.io.Reader;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JToolBar;
import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;
import javax.swing.JButton;
import javax.swing.JToggleButton;

public class MainWindow{

	private JFrame frame;
	private ProgressMonitor progressMonitor;
	private LoadOfficesWorker operation;
	
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
				/*
				 * DeliveryServicesListWindow window = new
				 * DeliveryServicesListWindow(); window.show();
				 * desktopPane.add(window);
				 */
				progressMonitor = new ProgressMonitor(frame, "Operation in progress...", "", 0, 100);
				progressMonitor.setProgress(0);

				operation = new LoadOfficesWorker();
				operation.addPropertyChangeListener(new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent event) {
						if (progressMonitor.isCanceled()) {
							operation.cancel(true);
						} else if (event.getPropertyName().equals("progress")) {
							int progress = ((Integer) event.getNewValue()).intValue();
							progressMonitor.setProgress(progress);
						}
					}
				});
				operation.execute();
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
	
	class ProgressData {
		private int progress;
		private String description;

		public ProgressData() {
		}

		public ProgressData(int progress, String description) {
			this.progress = progress;
			this.description = description;
		}

		public int getProgress() {
			return progress;
		}

		public void setProgress(int progress) {
			this.progress = progress;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
	}

	class LoadOfficesWorker extends SwingWorker<Void, ProgressData> {

		@Override
		protected Void doInBackground() throws Exception {

			for (int i = 1; i <= 100; i++) {
				if (isCancelled()) {
					break;
				}

				Thread.sleep(100);
				
				int progress = i;

				ProgressData current = new ProgressData(progress, "test: " + progress);
				setProgress(i);
				publish(current);
			}

			return null;
		}

		@Override
		public void process(List<ProgressData> data){
			if (isCancelled()) {
				return;
			}
			
			ProgressData update = new ProgressData();
			for (ProgressData d: data){
				if (d.getProgress() > update.getProgress()) {
					update = d;
				}
			}
			
			if (update.getProgress() < 100) {
				progressMonitor.setNote(update.getDescription());
			} else {
				progressMonitor.setNote("done!");
			}			
		}

		@Override
		public void done() {
			try {
				Void result = get();
				System.out.println(result);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CancellationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			progressMonitor.setProgress(0);
		}
	}

}
