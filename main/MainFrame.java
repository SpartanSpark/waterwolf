package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTextField addressBar;
	private JEditorPane display = new JEditorPane();
	private JScrollPane s;
//	private ImageCanvas i = new ImageCanvas();
	
	//constructor
	public MainFrame() {
		
		super("Hydro Wolf");
        
//		add(i, BorderLayout.SOUTH);
		
		addressBar = new JTextField();
		addressBar.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							 loadInput(e.getActionCommand()); 
						}
					}
				);
		add(addressBar, BorderLayout.NORTH);
		
//		display = new JEditorPane();
//		display.addHyperlinkListener(
//					new HyperlinkListener() {
//						public void hyperlinkUpdate(HyperlinkEvent e) {
//							if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
//								loadAddress(e.getURL().toString());
//							}
//						}
//					}
//				);
		
	    display.setContentType("text/html");
	    HTMLEditorKit hek = new HTMLEditorKit();
	    display.setEditorKit(hek);
	    display.setEditable(false);

	    s = new JScrollPane(display);
	    s.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(s, BorderLayout.CENTER);
//		add(display, BorderLayout.CENTER);
		
		setSize(1200,800);
		setVisible(true);
		
		loadAddress("https://www.google.com");
	}

	private void resetDisplay() {
		
//		re();
//		
//		display = new JEditorPane();
//		display.setContentType("text/html");
//	    HTMLEditorKit hek = new HTMLEditorKit();
//	    display.setEditorKit(hek);
//	    display.setEditable(false);
//
//	    ad();
		
	}
	
	private void insertImage(String src) {
		
		re();
		
		HTMLDocument doc = (HTMLDocument) display.getDocument();

	    javax.swing.text.Element[] roots = doc.getRootElements();
	    javax.swing.text.Element body = null;
	    for (int i = 0; i < roots[0].getElementCount(); i++) {
	    	javax.swing.text.Element element = roots[0].getElement(i);
	      if (element.getAttributes().getAttribute(StyleConstants.NameAttribute) == HTML.Tag.BODY) {
	        body = element;
	        break;
	      }
	    }

	    try {
			doc.insertAfterEnd(body,"<img src=" + src + ">");
		} catch (BadLocationException | IOException e1) {
			e1.printStackTrace();
		}

	    ad();
	    
	}
	
	private void insertText(String text) {
		
		re();
		
		HTMLDocument doc = (HTMLDocument) display.getDocument();

	    javax.swing.text.Element[] roots = doc.getRootElements();
	    javax.swing.text.Element body = null;
	    for (int i = 0; i < roots[0].getElementCount(); i++) {
	    	javax.swing.text.Element element = roots[0].getElement(i);
	      if (element.getAttributes().getAttribute(StyleConstants.NameAttribute) == HTML.Tag.BODY) {
	        body = element;
	        break;
	      }
	    }
		
	    try {
			doc.insertAfterEnd(body, "<p>" + text + "</p>");
		} catch (BadLocationException | IOException e2) {
			e2.printStackTrace();
		}

	    ad();
		
	}
	
//	private void insertElement(String el) {
//		
//		if(el != null && el != "") {
//		
//			re();
//			
//			HTMLDocument doc = (HTMLDocument) display.getDocument();
//	
//		    javax.swing.text.Element[] roots = doc.getRootElements();
//		    javax.swing.text.Element body = null;
//		    for (int i = 0; i < roots[0].getElementCount(); i++) {
//		    	javax.swing.text.Element element = roots[0].getElement(i);
//		      if (element.getAttributes().getAttribute(StyleConstants.NameAttribute) == HTML.Tag.BODY) {
//		        body = element;
//		        break;
//		      }
//		    }
//		    
//		    try {
//				doc.insertAfterEnd(body, el);
//			} catch (BadLocationException | IOException e2) {
//				e2.printStackTrace();
//			}
//	
//		    ad();
//	    
//		}
//		
//	}
	
	private void re() {
//		remove(display);
		remove(s);
		s.remove(display);
	}
	
	private void ad() {
		s.add(display);
		add(s, BorderLayout.CENTER);
//		add(s, BorderLayout.CENTER);
	}
	
	private void loadInput(String input) {

		String max = ""; //this is what will be sent, it will have the full url, it is also the maximum  url to enter
		String min = input; //this is what will be displayed on the top, it is also the minimum url to enter
		boolean foundPC = false; //checks if there is a found protocol, the subdomain is not needed, but the protocol is
		
		//checks if it is http (http is normal, as such there is no need to display it)
		if(min.length() >= 7 && min.substring(0,7).equals("http://")) {
			foundPC = true;
			max = "http://";
			min = min.substring(7);
		}
		//checks if it is https (https is normal, as such there is no need to display it)
		if(min.length() >= 8 && min.substring(0,8).equals("https://")) {
			foundPC = true;
			max = "https://";
			min = min.substring(8);
		}
		//checks if it is other
		if(!foundPC && min.contains("://")) {
			foundPC = true;
			max = min.substring(0,min.indexOf("://"));
			min = min.substring(min.indexOf("://"));
		}
		//checks if it is www (www is normal, as such there is no need to display it)
		if(min.length() >= 4 && min.substring(0,4).equals("www.")) {
			max += "www.";
			min = min.substring(4);
		}
		//adds the rest of min, to max
		max += min;
		//to check if you need to search
		boolean shouldSearch = false;
		//if there is no protocol found, try http and https protocols, if they don't work, try to search it up instead
		if(!foundPC) if(!loadURL("https://" + max)) if(!loadURL("http://" + max)) shouldSearch = true;
		//sets display to min
		addressBar.setText(min);
		//if there is a protocol found, try it
		if(foundPC) {
			try {
				//plez load
				load(max);
			} catch(Exception e) {
				//failed to load page, tries to search it up instead
				e.printStackTrace();
				shouldSearch = true;
			}
		}
		if(shouldSearch)
			try {
				loadDoc(max, GoogleSearch.query(min));
			} catch (IOException e) {
				e.printStackTrace();
				crap();
			}
	}
	
	private void crap() {
		display.setText("Crap!");
	}
	
	private boolean loadAddress(String url) {

		//sets text to just after the subdomain
		addressBar.setText(url.substring(url.indexOf(".") + 1));
		
		try {
			//plez load
	        load(url);
		} catch(Exception e) {
			e.printStackTrace();
			crap();
			return false;
		}
		return true;
		
	}
	
	private boolean loadURL(String url) {
		
		try {
			//plez load
	        load(url);
		} catch(Exception e) {
			//didn't work, return false, crap!
			e.printStackTrace();
			crap();
			return false;
		}
		return true;
		
	}
	
	private void load(String url) throws IOException {

        loadDoc(url, Jsoup.connect(url).get());
		
	}
	
	private void loadDoc(String url, Document doc) {

		resetDisplay();

//		for(Element e : doc.getAllElements()) insertElement(e.val());
		
//        remove(i);
        
//        display.setText(doc.title() + "\n" + doc.body().text());
//        insertText(doc.title());
        insertText(doc.body().text());
        Elements images = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
        setTitle(doc.title());
//        i.reset();
        for (Element image : images) {

//            for(org.jsoup.nodes.Attribute a : image.attributes()) System.out.println(a.getKey() + " " + a.getValue());
//            System.out.println(image.attr("style"));
            
//			try {
//				int x = 0;
//				int y = 0;
//				String style = image.attr("style");
//				if(style.indexOf("padding-top:") >= 0) y = Integer.parseInt(style.substring(style.indexOf("padding-top:") + 
//						"padding-top:".length(), style.substring(style.indexOf("padding-top:")).indexOf("px")));
//				if(image.attr("src").indexOf("/") == 0) i.add(new URL(url + image.attr("src")), x, y);
//				else i.add(new URL(image.attr("src")), x, y);
//			} catch (MalformedURLException e) {
//				e.printStackTrace();
//				crap();
//			}

			if(image.attr("src").indexOf("/") == 0) insertImage(url + image.attr("src"));
			else insertImage(image.attr("src"));
				
        }		
        
//        add(i);
        
	}
	
//	private void load(String url) throws IOException {
//
//        URL page = new URL(url);
//		display.setPage(page);
//		
//	}
	
}
