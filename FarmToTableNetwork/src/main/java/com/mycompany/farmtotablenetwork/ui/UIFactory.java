package com.mycompany.farmtotablenetwork.ui;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

/**
 * UIFactory — static factory methods for every styled component.
 *
 * Usage: replace new JButton("Save") with UIFactory.primaryButton("Save")
 * All team members call these methods. Never style components inline.
 */
public class UIFactory {

    // ─── HEADER ───────────────────────────────────────────────────────────────

    /**
     * Standard panel header. Drop this at the top (BorderLayout.NORTH) of every panel.
     *
     * @param title     Panel title shown in large text  (e.g. "Crop Management")
     * @param userName  Logged-in user's display name    (e.g. "Jane Smith")
     * @param userRole  Role string                      (e.g. "Farmer")
     */
    public static JPanel header(String title, String userName, String userRole) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UIConstants.BG_HEADER);
        header.setPreferredSize(new Dimension(0, UIConstants.HEADER_HEIGHT));
        header.setBorder(BorderFactory.createEmptyBorder(0, UIConstants.PADDING, 0, UIConstants.PADDING));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(UIConstants.FONT_HEADER_TITLE);
        titleLabel.setForeground(UIConstants.TEXT_ON_DARK);

        JPanel userInfo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        userInfo.setOpaque(false);
        JLabel nameLabel = new JLabel(userName);
        nameLabel.setFont(UIConstants.FONT_HEADER_SUB);
        nameLabel.setForeground(UIConstants.TEXT_ON_DARK);
        JLabel roleLabel = new JLabel("| " + userRole);
        roleLabel.setFont(UIConstants.FONT_HEADER_SUB);
        roleLabel.setForeground(new Color(0xAACC88)); // slightly dimmer green

        userInfo.add(nameLabel);
        userInfo.add(roleLabel);

        header.add(titleLabel, BorderLayout.CENTER);
        header.add(userInfo, BorderLayout.EAST);
        return header;
    }

    /**
     * Simplified header with title only (no user info) — use for Form and Detail panels.
     */
    public static JPanel headerSimple(String title) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UIConstants.BG_SUBHEADER);
        header.setPreferredSize(new Dimension(0, 48));
        header.setBorder(BorderFactory.createEmptyBorder(0, UIConstants.PADDING, 0, UIConstants.PADDING));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(UIConstants.FONT_SECTION_LABEL);
        titleLabel.setForeground(UIConstants.TEXT_ON_DARK);
        header.add(titleLabel, BorderLayout.CENTER);
        return header;
    }

    // ─── BUTTONS ──────────────────────────────────────────────────────────────

    /** Green filled button — main actions (New, Submit, Approve, Claim). */
    public static JButton primaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(UIConstants.FONT_BTN);
        btn.setBackground(UIConstants.BTN_PRIMARY_BG);
        btn.setForeground(UIConstants.BTN_PRIMARY_FG);
        btn.setPreferredSize(new Dimension(btn.getPreferredSize().width + 20, UIConstants.BTN_HEIGHT));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(UIConstants.BTN_PRIMARY_HOVER); }
            public void mouseExited(java.awt.event.MouseEvent e)  { btn.setBackground(UIConstants.BTN_PRIMARY_BG); }
        });
        return btn;
    }

    /** Tan/gray button — Back, Cancel, View Detail. */
    public static JButton secondaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(UIConstants.FONT_BTN);
        btn.setBackground(UIConstants.BTN_SECONDARY_BG);
        btn.setForeground(UIConstants.BTN_SECONDARY_FG);
        btn.setPreferredSize(new Dimension(btn.getPreferredSize().width + 20, UIConstants.BTN_HEIGHT));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    /** Red button — Deny, Delete, Reject. */
    public static JButton dangerButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(UIConstants.FONT_BTN);
        btn.setBackground(UIConstants.BTN_DANGER_BG);
        btn.setForeground(UIConstants.BTN_DANGER_FG);
        btn.setPreferredSize(new Dimension(btn.getPreferredSize().width + 20, UIConstants.BTN_HEIGHT));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ─── FORM FIELDS ──────────────────────────────────────────────────────────

    /** Labeled text field row. Returns a JPanel with label + field side by side. */
    public static JTextField labeledField(JPanel container, GridBagConstraints gbc,
                                          String labelText, int gridY) {
        gbc.gridx = 0; gbc.gridy = gridY; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        JLabel label = new JLabel(labelText);
        label.setFont(UIConstants.FONT_SECTION_LABEL);
        label.setForeground(UIConstants.TEXT_SECONDARY);
        container.add(label, gbc);

        gbc.gridx = 1; gbc.weightx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextField field = new JTextField();
        field.setFont(UIConstants.FONT_BODY);
        field.setPreferredSize(new Dimension(0, UIConstants.FIELD_HEIGHT));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIConstants.BORDER_MEDIUM),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        container.add(field, gbc);
        return field;
    }

    /** Labeled combo box row. Returns the JComboBox. */
    public static <T> JComboBox<T> labeledCombo(JPanel container, GridBagConstraints gbc,
                                                  String labelText, T[] items, int gridY) {
        gbc.gridx = 0; gbc.gridy = gridY; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        JLabel label = new JLabel(labelText);
        label.setFont(UIConstants.FONT_SECTION_LABEL);
        label.setForeground(UIConstants.TEXT_SECONDARY);
        container.add(label, gbc);

        gbc.gridx = 1; gbc.weightx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        JComboBox<T> combo = new JComboBox<>(items);
        combo.setFont(UIConstants.FONT_BODY);
        combo.setPreferredSize(new Dimension(0, UIConstants.FIELD_HEIGHT));
        container.add(combo, gbc);
        return combo;
    }

    /** Read-only label row for Detail panels. */
    public static void detailRow(JPanel container, GridBagConstraints gbc,
                                  String labelText, String value, int gridY) {
        gbc.gridx = 0; gbc.gridy = gridY; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        JLabel label = new JLabel(labelText);
        label.setFont(UIConstants.FONT_SECTION_LABEL);
        label.setForeground(UIConstants.TEXT_SECONDARY);
        container.add(label, gbc);

        gbc.gridx = 1; gbc.weightx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel val = new JLabel(value == null ? "—" : value);
        val.setFont(UIConstants.FONT_BODY);
        val.setForeground(UIConstants.TEXT_PRIMARY);
        container.add(val, gbc);
    }

    /** Inline validation error label — place below the form grid. Initially invisible. */
    public static JLabel errorLabel() {
        JLabel lbl = new JLabel(" ");
        lbl.setFont(UIConstants.FONT_ERROR);
        lbl.setForeground(UIConstants.BTN_DANGER_BG);
        return lbl;
    }

    // ─── STATUS BADGE ─────────────────────────────────────────────────────────

    /**
     * Returns a color-coded JLabel for any status string.
     * Maps status to background/foreground using StatusConstants values.
     */
    public static JLabel statusBadge(String status) {
        JLabel badge = new JLabel(" " + status + " ") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        badge.setFont(UIConstants.FONT_STATUS_BADGE);
        badge.setOpaque(false);
        badge.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));

        // Map status string → colors
        Color bg, fg;
        switch (status == null ? "" : status) {
            case StatusConstants.APPROVED:
            case StatusConstants.PASSED:
            case StatusConstants.PACKAGED:
            case StatusConstants.STOCKED:
            case StatusConstants.RECEIVED:
                bg = UIConstants.STATUS_APPROVED_BG; fg = UIConstants.STATUS_APPROVED_FG; break;
            case StatusConstants.IN_PROGRESS:
            case StatusConstants.FULFILLING:
            case StatusConstants.IN_TRANSIT:
            case StatusConstants.ASSIGNED:
            case StatusConstants.REVIEWED:
                bg = UIConstants.STATUS_INPROGRESS_BG; fg = UIConstants.STATUS_INPROGRESS_FG; break;
            case StatusConstants.FAILED:
            case StatusConstants.DENIED:
            case StatusConstants.REJECTED:
                bg = UIConstants.STATUS_FAILED_BG; fg = UIConstants.STATUS_FAILED_FG; break;
            case StatusConstants.DELIVERED:
            case StatusConstants.SHIPPED:
            case StatusConstants.CONFIRMED:
                bg = UIConstants.STATUS_DELIVERED_BG; fg = UIConstants.STATUS_DELIVERED_FG; break;
            default: // Submitted, Pending, Pending Review, Requested
                bg = UIConstants.STATUS_SUBMITTED_BG; fg = UIConstants.STATUS_SUBMITTED_FG; break;
        }
        badge.setBackground(bg);
        badge.setForeground(fg);
        return badge;
    }

    // ─── TABLE ────────────────────────────────────────────────────────────────

    /**
     * Styled JTable with alternating row colors, custom header, and row height.
     * The domain object should be stored in column index 0 (it will be hidden
     * or shown as an ID depending on your column definition).
     *
     * @param model  A DefaultTableModel with your column names and data
     */
    public static JTable styledTable(DefaultTableModel model) {
        JTable table = new JTable(model) {
            // Alternating row colors
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? UIConstants.BG_PANEL : UIConstants.BG_TABLE_ALT);
                } else {
                    c.setBackground(UIConstants.BG_TABLE_SEL);
                }
                c.setForeground(UIConstants.TEXT_PRIMARY);
                return c;
            }
        };

        table.setFont(UIConstants.FONT_TABLE_BODY);
        table.setRowHeight(UIConstants.TABLE_ROW_HEIGHT);
        table.setGridColor(UIConstants.BORDER_LIGHT);
        table.setShowGrid(true);
        table.setSelectionBackground(UIConstants.BG_TABLE_SEL);
        table.setSelectionForeground(UIConstants.TEXT_PRIMARY);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setFont(UIConstants.FONT_TABLE_HEADER);
        table.getTableHeader().setBackground(UIConstants.BG_HEADER);
        table.getTableHeader().setForeground(UIConstants.TEXT_ON_DARK);
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return table;
    }

    // ─── SECTION DIVIDER ──────────────────────────────────────────────────────

    /** Thin horizontal rule with a section label. Add to form panels between groups. */
    public static JPanel sectionDivider(String label) {
        JPanel p = new JPanel(new BorderLayout(8, 0));
        p.setOpaque(false);
        JLabel lbl = new JLabel(label);
        lbl.setFont(UIConstants.FONT_SECTION_LABEL);
        lbl.setForeground(UIConstants.TEXT_SECONDARY);
        JSeparator sep = new JSeparator();
        sep.setForeground(UIConstants.BORDER_LIGHT);
        p.add(lbl, BorderLayout.WEST);
        p.add(sep, BorderLayout.CENTER);
        return p;
    }

    /** Standard scroll pane wrapping a table. */
    public static JScrollPane tableScrollPane(JTable table) {
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_LIGHT));
        scroll.getViewport().setBackground(UIConstants.BG_PANEL);
        return scroll;
    }
}
