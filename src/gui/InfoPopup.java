package gui;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class InfoPopup {

	private JFrame infoPopup;
	private JPanel panel_1;
	private JPanel panel;

	//TODO: Nach Debugging entfernen - nur showInfo() nutzen
	public static void main(String[] args) {
		showInfo(args);
	}
	
	/**
	 * Launch the application.
	 */
	public static void showInfo(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InfoPopup window = new InfoPopup();
					window.infoPopup.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InfoPopup() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		infoPopup = new JFrame();
		infoPopup.setTitle("BFS Tool -- GKA WS 14/15 (Okt. 2014)");
		infoPopup.setResizable(false);
		infoPopup.setType(Type.UTILITY);
		infoPopup.setBounds(100, 100, 418, 300);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		infoPopup.setLocation(
				(dim.width / 2 - infoPopup.getSize().width / 2),
				(dim.height / 2 - infoPopup.getSize().height / 2));
		infoPopup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		infoPopup.getContentPane().setLayout(null);
		
		try {
			BufferedImage profileLouisa = 	ImageIO.read(new File(".\\src\\ressources\\images\\profile_lou.png".replace("\\", "/")));
			BufferedImage profileTim 	= 	ImageIO.read(new File(".\\src\\ressources\\images\\profile_tim.png".replace("\\", "/")));
			
			panel = new JPanel();
			panel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent arg0) {
					panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
				}
				@Override
				public void mouseExited(MouseEvent arg0) {
					panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
				}
			});
			panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			panel.setBounds(15, 50, 169, 180);
			infoPopup.getContentPane().add(panel);
			panel.setLayout(null);
			
			JLabel picLouisa = new JLabel(new ImageIcon(profileLouisa));
			picLouisa.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					try {
						openWebpage(new URL("https://github.com/Isaisaisa"));
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
				}
				@Override
				public void mouseEntered(MouseEvent arg0) {
					panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
				}
				@Override
				public void mouseExited(MouseEvent arg0) {
					panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
				}
			});
			picLouisa.setBounds(10, 18, 150, 150);
			panel.add(picLouisa);
			
			panel_1 = new JPanel();
			panel_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent arg0) {
					panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
				}
				@Override
				public void mouseExited(MouseEvent arg0) {
					panel_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
				}
			});
			panel_1.setLayout(null);
			panel_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			panel_1.setBounds(228, 50, 169, 180);
			infoPopup.getContentPane().add(panel_1);
			panel_1.setLayout(null);
			
			JLabel picTim = new JLabel(new ImageIcon(profileTim));
			picTim.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					try {
						openWebpage(new URL("https://github.com/Timmay24"));
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
				}
				@Override
				public void mouseEntered(MouseEvent arg0) {
					panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
				}
				@Override
				public void mouseExited(MouseEvent arg0) {
					panel_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
				}
			});
			picTim.setBounds(10, 18, 150, 150);
			panel_1.add(picTim);
			
			JButton btnClose = new JButton("Schlie\u00DFen");
			btnClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					infoPopup.dispose();
				}
			});
			btnClose.setBounds(156, 242, 95, 23);
			infoPopup.getContentPane().add(btnClose);
			
			JLabel lblInfo = new JLabel("Diese Praktikumsaufgabe wurde erstellt von:");
			lblInfo.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
			lblInfo.setBounds(10, 11, 392, 28);
			infoPopup.getContentPane().add(lblInfo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void openWebpage(URI uri) {
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(uri);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

	public static void openWebpage(URL url) {
	    try {
	        openWebpage(url.toURI());
	    } catch (URISyntaxException e) {
	        e.printStackTrace();
	    }
	}
}
