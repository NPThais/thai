package com.tlk.swing;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import static javax.swing.text.StyleConstants.Bold;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class ButtonBig1 extends javax.swing.JButton {

    public Color getEffectColor() {
        return effectColor;
    }

    public void setEffectColor(Color effectColor) {
        this.effectColor = effectColor;
    }

    private Animator animator;
    private int targetSize;
    private float animatSize;
    private Point pressedPoint;
    private float alpha;
    private Color effectColor = new Color(53, 155, 221  );

    public ButtonBig1() {
        setContentAreaFilled(false);
        setBorder(new EmptyBorder(5, 0, 5, 0));
        setFont(new Font("Segoe UI", Font.BOLD, 14));
        setBackground(new Color(51,102,255));
        setForeground(new Color(255,255,255));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                targetSize = Math.max(getWidth(), getHeight()) * 2;
                animatSize = 0;
                pressedPoint = me.getPoint();
                alpha = 0.5f;
                if (animator.isRunning()) {
                    animator.stop();
                }
                animator.start();
            }
        });
        
        
        
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                if (fraction > 0.5f) {
                    alpha = 1 - fraction;
                }
                animatSize = fraction * targetSize;
                repaint();
            }
        };
        animator = new Animator(700, target);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.setResolution(0);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        int width = getWidth();
        int height = getHeight();
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, width, height, height, height);
        if (pressedPoint != null) {
            g2.setColor(new Color(79, 117, 237 ));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            g2.fillOval((int) (pressedPoint.x - animatSize / 2), (int) (pressedPoint.y - animatSize / 2), (int) animatSize, (int) animatSize);
        }
        g2.dispose();
        grphcs.drawImage(img, 0, 0, null);
        super.paintComponent(grphcs);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
