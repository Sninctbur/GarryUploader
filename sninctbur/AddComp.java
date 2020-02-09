package sninctbur;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JPanel;

public interface AddComp {
		/**
		 * Adds an instantiated component to the frame with a position according to given variables.
		 * @param p The JPanel to add c to
		 * @param c The JComponent to be added to p
		 * @param col The column number
		 * @param row The row number
		 * @param w The width of c
		 * @param h The height of c
		 * @param place The anchor of the component (GridBagConstraints enums recommended)
		 * @param stretch You should probably just put GridBagConstraints.NONE here unless you know what you are doing
		 */
		static void addComp(JPanel p,JComponent c,int col,int row,int w,int h,int place,int stretch) {
			GridBagConstraints gridC = new GridBagConstraints();
			gridC.gridx = col;
			gridC.gridy = row;
			gridC.gridwidth = w;
			gridC.gridheight = h;
			gridC.insets = new Insets(5,5,5,5);
			gridC.anchor = place;
			gridC.fill = stretch;
			p.add(c,gridC);
		}
	}