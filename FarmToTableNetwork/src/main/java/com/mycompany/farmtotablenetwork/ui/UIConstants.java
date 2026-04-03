package com.mycompany.farmtotablenetwork.ui;

import java.awt.Color;
import java.awt.Font;

/**
 * UIConstants — shared palette and typography for the Farm-to-Table UI.
 * All team members reference these constants. Never hardcode colors or fonts.
 */
public class UIConstants {

    // ─── BACKGROUND ───────────────────────────────────────────────────────────
    public static final Color BG_APP        = new Color(0xF5F2ED); // warm off-white, app background
    public static final Color BG_PANEL      = new Color(0xFFFFFF); // white, card/panel background
    public static final Color BG_HEADER     = new Color(0x2D5016); // deep forest green, top header bar
    public static final Color BG_SUBHEADER  = new Color(0x4A7C2F); // medium green, secondary bars
    public static final Color BG_TABLE_ALT  = new Color(0xF0EDE6); // light tan, alternating table rows
    public static final Color BG_TABLE_SEL  = new Color(0xC8DFB0); // sage green, selected row

    // ─── TEXT ─────────────────────────────────────────────────────────────────
    public static final Color TEXT_PRIMARY   = new Color(0x1A1A1A); // near-black, body text
    public static final Color TEXT_SECONDARY = new Color(0x5C5C5C); // gray, labels/hints
    public static final Color TEXT_ON_DARK   = new Color(0xF5F2ED); // light, text on dark headers
    public static final Color TEXT_LINK      = new Color(0x2D5016); // green, clickable text

    // ─── BORDER ───────────────────────────────────────────────────────────────
    public static final Color BORDER_LIGHT  = new Color(0xDDD8CF); // subtle dividers
    public static final Color BORDER_MEDIUM = new Color(0xB0A898); // form field borders

    // ─── STATUS BADGE COLORS ──────────────────────────────────────────────────
    // Background / Foreground pairs for each status group
    public static final Color STATUS_SUBMITTED_BG   = new Color(0xE8E8E8);
    public static final Color STATUS_SUBMITTED_FG   = new Color(0x444444);

    public static final Color STATUS_INPROGRESS_BG  = new Color(0xFFF3CD);
    public static final Color STATUS_INPROGRESS_FG  = new Color(0x7A5C00);

    public static final Color STATUS_APPROVED_BG    = new Color(0xD4EDDA);
    public static final Color STATUS_APPROVED_FG    = new Color(0x1A5C2A);

    public static final Color STATUS_FAILED_BG      = new Color(0xF8D7DA);
    public static final Color STATUS_FAILED_FG      = new Color(0x7A1020);

    public static final Color STATUS_DENIED_BG      = new Color(0xF8D7DA);
    public static final Color STATUS_DENIED_FG      = new Color(0x7A1020);

    public static final Color STATUS_DELIVERED_BG   = new Color(0xD1ECF1);
    public static final Color STATUS_DELIVERED_FG   = new Color(0x0C5460);

    // ─── BUTTON COLORS ────────────────────────────────────────────────────────
    // Primary — main action (New, Submit, Approve, Claim)
    public static final Color BTN_PRIMARY_BG    = new Color(0x4A7C2F);
    public static final Color BTN_PRIMARY_FG    = Color.WHITE;
    public static final Color BTN_PRIMARY_HOVER = new Color(0x2D5016);

    // Secondary — navigation and neutral (Back, Cancel, View Detail)
    public static final Color BTN_SECONDARY_BG  = new Color(0xE8E4DC);
    public static final Color BTN_SECONDARY_FG  = new Color(0x333333);

    // Destructive — irreversible actions (Delete, Deny, Reject)
    public static final Color BTN_DANGER_BG     = new Color(0xC0392B);
    public static final Color BTN_DANGER_FG     = Color.WHITE;

    // ─── TYPOGRAPHY ───────────────────────────────────────────────────────────
    public static final Font FONT_HEADER_TITLE  = new Font("SansSerif", Font.BOLD,   20);
    public static final Font FONT_HEADER_SUB    = new Font("SansSerif", Font.PLAIN,  12);
    public static final Font FONT_SECTION_LABEL = new Font("SansSerif", Font.BOLD,   13);
    public static final Font FONT_BODY          = new Font("SansSerif", Font.PLAIN,  13);
    public static final Font FONT_TABLE_HEADER  = new Font("SansSerif", Font.BOLD,   12);
    public static final Font FONT_TABLE_BODY    = new Font("SansSerif", Font.PLAIN,  12);
    public static final Font FONT_STATUS_BADGE  = new Font("SansSerif", Font.BOLD,   11);
    public static final Font FONT_BTN           = new Font("SansSerif", Font.BOLD,   12);
    public static final Font FONT_ERROR         = new Font("SansSerif", Font.ITALIC, 12);

    // ─── SIZING ───────────────────────────────────────────────────────────────
    public static final int HEADER_HEIGHT   = 64;
    public static final int BTN_HEIGHT      = 34;
    public static final int FIELD_HEIGHT    = 30;
    public static final int TABLE_ROW_HEIGHT = 28;
    public static final int PADDING         = 16;
}
