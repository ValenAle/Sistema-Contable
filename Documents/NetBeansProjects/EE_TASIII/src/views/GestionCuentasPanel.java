package views;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Panel principal de Gestión de Cuentas - Sistema Contable
 * Replicado con estilo moderno usando Java Swing puro.
 */
public class GestionCuentasPanel extends JFrame {

    // ── Paleta de colores ──────────────────────────────────────────────────────
    private static final Color AZUL_HEADER   = new Color(30,  80, 160);
    private static final Color AZUL_OSCURO   = new Color(20,  55, 120);
    private static final Color AZUL_BOTON    = new Color(33, 118, 233);
    private static final Color VERDE_BOTON   = new Color(40, 167,  69);
    private static final Color ROJO_BOTON    = new Color(220,  53,  69);
    private static final Color GRIS_BOTON    = new Color(108, 117, 125);
    private static final Color FONDO_PANEL   = new Color(245, 247, 250);
    private static final Color BORDE_COLOR   = new Color(220, 225, 235);
    private static final Color AZUL_TAB      = new Color(33, 118, 233);
    private static final Color TEXTO_LABEL   = new Color( 50,  60,  80);
    private static final Color FONDO_CAMPO   = new Color(250, 251, 253);
    private static final Color PLACEHOLDER   = new Color(170, 175, 185);
    private static final Color VERDE_BADGE   = new Color(212, 237, 218);
    private static final Color VERDE_TEXTO   = new Color( 21, 128,  61);
    private static final Color ROJO_BADGE    = new Color(248, 215, 218);
    private static final Color ROJO_TEXTO    = new Color(155,  28,  48);
    private static final Color FILA_PAR      = Color.WHITE;
    private static final Color FILA_IMPAR    = new Color(248, 250, 253);
    private static final Color CABECERA_TABLA= new Color(240, 243, 250);

    // ── Fuentes ────────────────────────────────────────────────────────────────
    private static final Font FUENTE_TITULO  = new Font("Segoe UI", Font.BOLD,  18);
    private static final Font FUENTE_SUBTIT  = new Font("Segoe UI", Font.PLAIN, 11);
    private static final Font FUENTE_SECCION = new Font("Segoe UI", Font.BOLD,  13);
    private static final Font FUENTE_LABEL   = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FUENTE_CAMPO   = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FUENTE_BOTON   = new Font("Segoe UI", Font.BOLD,  13);
    private static final Font FUENTE_TABLA   = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FUENTE_CAB     = new Font("Segoe UI", Font.BOLD,  13);
    private static final Font FUENTE_INFO    = new Font("Segoe UI", Font.PLAIN, 12);
    // ── Componentes de datos ───────────────────────────────────────────────────
    private JTextField txtBuscar;
    private JComboBox<String> cboTipoSaldo;
    private JTable      tablaCuentas;
    private DefaultTableModel modeloTabla;
    private JLabel      lblPaginacion;

    public GestionCuentasPanel() {
        configurarVentana();
        construirUI();
        cargarDatosDemostracion();
        setVisible(true);
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  CONFIGURACIÓN DE VENTANA
    // ══════════════════════════════════════════════════════════════════════════
    private void configurarVentana() {
        setTitle("Gestión de Cuentas - Sistema Contable");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 780);
        setMinimumSize(new Dimension(900, 620));
        setLocationRelativeTo(null);
        setBackground(FONDO_PANEL);
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ignored) {}
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  CONSTRUCCIÓN DE UI
    // ══════════════════════════════════════════════════════════════════════════
    private void construirUI() {
        setLayout(new BorderLayout());

        // ── Header azul ──────────────────────────────────────────────────────
        add(crearHeader(), BorderLayout.NORTH);

        // ── Cuerpo principal ─────────────────────────────────────────────────
        JPanel cuerpo = new JPanel(new BorderLayout());
        cuerpo.setBackground(FONDO_PANEL);
        cuerpo.setBorder(new EmptyBorder(0, 0, 0, 0));

        // Contenido central con scroll
        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBackground(FONDO_PANEL);
        contenido.setBorder(new EmptyBorder(20, 24, 20, 24));
        contenido.add(crearSeccionDatos());
        contenido.add(Box.createVerticalStrut(20));
        contenido.add(crearSeccionBusqueda());

        JScrollPane scroll = new JScrollPane(contenido);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setBackground(FONDO_PANEL);
        cuerpo.add(scroll, BorderLayout.CENTER);

        add(cuerpo, BorderLayout.CENTER);
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  HEADER
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel crearHeader() {
        JPanel header = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, AZUL_HEADER, getWidth(), 0, AZUL_OSCURO);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        header.setPreferredSize(new Dimension(0, 88));
        header.setBorder(new EmptyBorder(10, 20, 10, 20));

        // Ícono + títulos
        JPanel izq = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 0));
        izq.setOpaque(false);

        // Ícono banco
        JLabel icono = new JLabel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 50));
                g2.fillOval(0, 0, 46, 46);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 24));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString("🏛", (46 - fm.stringWidth("🏛")) / 2, 31);
            }
        };
        icono.setPreferredSize(new Dimension(46, 46));

        JPanel titulos = new JPanel();
        titulos.setLayout(new BoxLayout(titulos, BoxLayout.Y_AXIS));
        titulos.setOpaque(false);
        JLabel lblTitulo = new JLabel("GESTIÓN DE CUENTAS");
        lblTitulo.setFont(FUENTE_TITULO);
        lblTitulo.setForeground(Color.WHITE);
        JLabel lblSubtitulo = new JLabel("Sistema Contable");
        lblSubtitulo.setFont(FUENTE_SUBTIT);
        lblSubtitulo.setForeground(new Color(200, 215, 240));
        titulos.add(lblTitulo);
        titulos.add(lblSubtitulo);

        izq.add(icono);
        izq.add(titulos);
        header.add(izq, BorderLayout.WEST);

        // Controles de ventana
        JPanel der = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        der.setOpaque(false);
        String[] simbolos  = {"-", "[ ]", "X"};
        for (String sym : simbolos) {
            JButton btn = new JButton(sym) {
                @Override protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    if (getModel().isRollover()) {
                        g2.setColor(new Color(255, 255, 255, 40));
                        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                    }
                    g2.setColor(Color.WHITE);
                    g2.setFont(getFont());
                    FontMetrics fm = g2.getFontMetrics();
                    g2.drawString(getText(),
                        (getWidth()  - fm.stringWidth(getText())) / 2,
                        (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                }
            };
            btn.setPreferredSize(new Dimension(38, 28));
            btn.setOpaque(false); btn.setContentAreaFilled(false);
            btn.setBorderPainted(false); btn.setFocusPainted(false);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
            der.add(btn);
        }
        header.add(der, BorderLayout.EAST);
        return header;
    }
    // ══════════════════════════════════════════════════════════════════════════
    //  PESTAÑAS
    // ══════════════════════════════════════════════════════════════════════════

    // ══════════════════════════════════════════════════════════════════════════
    //  SECCIÓN: DATOS DE LA CUENTA
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel crearSeccionDatos() {
        JPanel tarjeta = crearTarjeta();
        tarjeta.setLayout(new BorderLayout(20, 0));

        // Título de sección
        JPanel norte = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        norte.setOpaque(false);
        norte.setBorder(new EmptyBorder(0, 0, 16, 0));
        JLabel titulo = new JLabel("[ + ]  DATOS DE LA CUENTA");
        titulo.setFont(FUENTE_SECCION);
        titulo.setForeground(AZUL_TAB);
        norte.add(titulo);

        // Panel izquierdo: formulario
        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 0, 6, 12);
        gbc.anchor = GridBagConstraints.WEST;

        // Código
        agregarCampo(formulario, gbc, 0, "Código:", crearCampoTexto("(AUTOMÁTICO)", true));
        // Nombre
        agregarCampo(formulario, gbc, 1, "Nombre:", crearCampoTexto("Ingrese el nombre de la cuenta", false));
        // Saldo
        agregarCampo(formulario, gbc, 2, "Saldo:", crearCampoTexto("0,00", true));
        // Tipo de saldo
        cboTipoSaldo = new JComboBox<>(new String[]{"Seleccione...", "Deudor", "Acreedor"});
        estilizarCombo(cboTipoSaldo);
        agregarCampo(formulario, gbc, 3, "Tipo de saldo:", cboTipoSaldo);

        // Aviso
        JPanel aviso = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        aviso.setBackground(new Color(232, 244, 255));
        aviso.setBorder(new CompoundBorder(
            new LineBorder(new Color(190, 220, 250), 1, true),
            new EmptyBorder(2, 6, 2, 6)
        ));
        JLabel icAviso = new JLabel("(i)");
        icAviso.setFont(new Font("Segoe UI", Font.BOLD, 13));
        icAviso.setForeground(new Color(30, 100, 200));
        JLabel txtAviso = new JLabel("El saldo se inicializa en 0,00 y no puede ser modificado.");
        txtAviso.setFont(FUENTE_INFO);
        txtAviso.setForeground(new Color(40, 80, 160));
        aviso.add(icAviso); aviso.add(txtAviso);

        // Envolver formulario + aviso
        JPanel izq = new JPanel(new BorderLayout());
        izq.setOpaque(false);
        izq.add(formulario, BorderLayout.CENTER);
        izq.add(aviso, BorderLayout.SOUTH);

        // Panel derecho: botones
        JPanel botones = new JPanel();
        botones.setLayout(new BoxLayout(botones, BoxLayout.Y_AXIS));
        botones.setOpaque(false);
        botones.setBorder(new EmptyBorder(0, 10, 0, 0));
        botones.setPreferredSize(new Dimension(200, 0));

        botones.add(crearBoton("CREAR",      VERDE_BOTON));
        botones.add(Box.createVerticalStrut(10));
        botones.add(crearBoton("ACTUALIZAR", AZUL_BOTON));
        botones.add(Box.createVerticalStrut(10));
        botones.add(crearBoton("ELIMINAR",   ROJO_BOTON));
        botones.add(Box.createVerticalStrut(10));
        botones.add(crearBoton("LIMPIAR",     GRIS_BOTON));

        // Contenedor centro
        JPanel centro = new JPanel(new BorderLayout());
        centro.setOpaque(false);
        centro.add(norte, BorderLayout.NORTH);
        centro.add(izq, BorderLayout.CENTER);

        tarjeta.add(centro, BorderLayout.CENTER);
        tarjeta.add(botones, BorderLayout.EAST);
        return tarjeta;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  SECCIÓN: BUSCAR CUENTAS
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel crearSeccionBusqueda() {
        JPanel tarjeta = crearTarjeta();
        tarjeta.setLayout(new BorderLayout(0, 14));

        // Título
        JLabel titulo = new JLabel("BUSCAR CUENTAS");
        titulo.setFont(FUENTE_SECCION);
        titulo.setForeground(AZUL_TAB);
        titulo.setBorder(new EmptyBorder(0, 0, 6, 0));

        // Barra de búsqueda
        JPanel barraBusq = new JPanel(new BorderLayout(10, 0));
        barraBusq.setOpaque(false);

        txtBuscar = crearCampoTexto("Ingrese nombre de cuenta...", false);
        JButton btnBuscar = crearBotonPequeno("BUSCAR", AZUL_BOTON);
        JButton btnRefresh = crearBotonPequeno("REFRESCAR", Color.WHITE);
        btnRefresh.setForeground(AZUL_BOTON);
        btnRefresh.setBorder(new LineBorder(AZUL_BOTON, 1, true));

        JPanel izqBarra = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        izqBarra.setOpaque(false);
        izqBarra.add(txtBuscar);
        izqBarra.add(btnBuscar);

        JPanel derBarra = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        derBarra.setOpaque(false);
        derBarra.add(btnRefresh);

        barraBusq.add(izqBarra, BorderLayout.WEST);
        barraBusq.add(derBarra, BorderLayout.EAST);

        // Tabla
        String[] columnas = {"Código", "Nombre", "Saldo", "Tipo de Saldo"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaCuentas = new JTable(modeloTabla) {
            @Override public Component prepareRenderer(TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                if (!isRowSelected(row))
                    c.setBackground(row % 2 == 0 ? FILA_PAR : FILA_IMPAR);
                return c;
            }
        };
        estilizarTabla();

        JScrollPane scrollTabla = new JScrollPane(tablaCuentas);
        scrollTabla.setBorder(new LineBorder(BORDE_COLOR, 1, true));
        scrollTabla.getViewport().setBackground(Color.WHITE);

        // Paginación
        JPanel paginacion = crearPaginacion();

        // Norte de la tarjeta
        JPanel norte = new JPanel(new BorderLayout());
        norte.setOpaque(false);
        norte.add(titulo, BorderLayout.NORTH);
        norte.add(barraBusq, BorderLayout.CENTER);

        tarjeta.add(norte, BorderLayout.NORTH);
        tarjeta.add(scrollTabla, BorderLayout.CENTER);
        tarjeta.add(paginacion, BorderLayout.SOUTH);
        return tarjeta;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  HELPERS DE COMPONENTES
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel crearTarjeta() {
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        p.setBorder(new CompoundBorder(
            new LineBorder(BORDE_COLOR, 1, true),
            new EmptyBorder(20, 22, 20, 22)
        ));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        return p;
    }

    private JTextField crearCampoTexto(String placeholder, boolean disabled) {
        JTextField tf = new JTextField(30) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    g.setColor(PLACEHOLDER);
                    g.setFont(FUENTE_CAMPO.deriveFont(Font.ITALIC));
                    g.drawString(placeholder, 10, getHeight() / 2 + 5);
                }
            }
        };
        tf.setFont(FUENTE_CAMPO);
        tf.setForeground(TEXTO_LABEL);
        tf.setBackground(disabled ? new Color(240, 243, 248) : FONDO_CAMPO);
        tf.setEnabled(!disabled);
        tf.setBorder(new CompoundBorder(
            new LineBorder(BORDE_COLOR, 1, true),
            new EmptyBorder(6, 10, 6, 10)
        ));
        tf.setPreferredSize(new Dimension(440, 38));
        return tf;
    }

    private void estilizarCombo(JComboBox<String> cb) {
        cb.setFont(FUENTE_CAMPO);
        cb.setForeground(TEXTO_LABEL);
        cb.setBackground(FONDO_CAMPO);
        cb.setBorder(new LineBorder(BORDE_COLOR, 1, true));
        cb.setPreferredSize(new Dimension(440, 38));
    }

    private void agregarCampo(JPanel p, GridBagConstraints gbc, int fila, String lbl, JComponent campo) {
        gbc.gridx = 0; gbc.gridy = fila; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        JLabel label = new JLabel(lbl);
        label.setFont(FUENTE_LABEL);
        label.setForeground(TEXTO_LABEL);
        label.setPreferredSize(new Dimension(130, 38));
        p.add(label, gbc);

        gbc.gridx = 1; gbc.weightx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        p.add(campo, gbc);
    }

    private JButton crearBoton(String texto, Color bg) {
        JButton btn = new JButton(texto) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color c = getModel().isRollover() ? bg.darker() : bg;
                g2.setColor(c);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                g2.setColor(Color.WHITE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth()  - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
            }
        };
        btn.setFont(FUENTE_BOTON);
        btn.setPreferredSize(new Dimension(190, 46));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setOpaque(false); btn.setContentAreaFilled(false);
        btn.setBorderPainted(false); btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton crearBotonPequeno(String texto, Color bg) {
        JButton btn = new JButton(texto) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? bg.equals(Color.WHITE) ? new Color(240,245,255) : bg.darker() : bg);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 6, 6));
                g2.setColor(getForeground());
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                    (getWidth()  - fm.stringWidth(getText())) / 2,
                    (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(bg.equals(Color.WHITE) ? AZUL_BOTON : Color.WHITE);
        btn.setPreferredSize(new Dimension(130, 36));
        btn.setOpaque(false); btn.setContentAreaFilled(false);
        btn.setBorderPainted(false); btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void estilizarTabla() {
        tablaCuentas.setFont(FUENTE_TABLA);
        tablaCuentas.setRowHeight(38);
        tablaCuentas.setShowGrid(false);
        tablaCuentas.setIntercellSpacing(new Dimension(0, 0));
        tablaCuentas.setSelectionBackground(new Color(210, 230, 255));
        tablaCuentas.setSelectionForeground(TEXTO_LABEL);
        tablaCuentas.setFillsViewportHeight(true);
        tablaCuentas.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Cabecera
        JTableHeader header = tablaCuentas.getTableHeader();
        header.setFont(FUENTE_CAB);
        header.setBackground(CABECERA_TABLA);
        header.setForeground(TEXTO_LABEL);
        header.setBorder(new MatteBorder(0, 0, 1, 0, BORDE_COLOR));
        header.setPreferredSize(new Dimension(0, 42));
        header.setReorderingAllowed(false);

        // Columna "Tipo de Saldo" con badges
        tablaCuentas.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(
                    JTable t, Object v, boolean sel, boolean foc, int r, int c) {
                JLabel lbl = new JLabel(v != null ? v.toString() : "");
                lbl.setOpaque(true);
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                String val = v != null ? v.toString() : "";
                if (val.equals("Deudor")) {
                    lbl.setBackground(VERDE_BADGE); lbl.setForeground(VERDE_TEXTO);
                } else {
                    lbl.setBackground(ROJO_BADGE);  lbl.setForeground(ROJO_TEXTO);
                }
                lbl.setBorder(new EmptyBorder(4, 12, 4, 12));

                JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));
                wrapper.setBackground(sel ? new Color(210, 230, 255) : (r % 2 == 0 ? FILA_PAR : FILA_IMPAR));
                wrapper.add(lbl);
                return wrapper;
            }
        });

        // Centrar columnas de número
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        tablaCuentas.getColumnModel().getColumn(0).setCellRenderer(center);
        tablaCuentas.getColumnModel().getColumn(2).setCellRenderer(center);

        // Ancho de columnas
        tablaCuentas.getColumnModel().getColumn(0).setPreferredWidth(100);
        tablaCuentas.getColumnModel().getColumn(1).setPreferredWidth(350);
        tablaCuentas.getColumnModel().getColumn(2).setPreferredWidth(150);
        tablaCuentas.getColumnModel().getColumn(3).setPreferredWidth(160);
    }

    private JPanel crearPaginacion() {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(10, 0, 0, 0));

        lblPaginacion = new JLabel("");
        lblPaginacion.setFont(FUENTE_INFO);
        lblPaginacion.setForeground(new Color(100, 110, 130));

        JPanel btnsPag = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 0));
        btnsPag.setOpaque(false);
        btnsPag.add(crearBotonPaginacion("<"));
        JButton btnUno = crearBotonPaginacion("1");
        btnUno.setBackground(AZUL_BOTON);
        btnUno.setForeground(Color.WHITE);
        btnsPag.add(btnUno);
        btnsPag.add(crearBotonPaginacion(">"));

        p.add(lblPaginacion, BorderLayout.WEST);
        p.add(btnsPag,       BorderLayout.EAST);
        return p;
    }

    private void actualizarLblPaginacion() {
        int total = modeloTabla.getRowCount();
        if (total == 0) {
            lblPaginacion.setText("Sin registros");
        } else {
            lblPaginacion.setText("Mostrando 1 a " + total + " de " + total + " registro" + (total == 1 ? "" : "s"));
        }
    }

    private JButton crearBotonPaginacion(String texto) {
        JButton btn = new JButton(texto) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = getBackground();
                g2.setColor(getModel().isRollover() ? bg.equals(AZUL_BOTON) ? AZUL_BOTON.darker() : new Color(230,235,245) : bg);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 6, 6));
                g2.setColor(getForeground());
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                    (getWidth()  - fm.stringWidth(getText())) / 2,
                    (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setBackground(new Color(235, 240, 250));
        btn.setForeground(new Color(60, 70, 90));
        btn.setPreferredSize(new Dimension(34, 34));
        btn.setOpaque(false); btn.setContentAreaFilled(false);
        btn.setBorderPainted(false); btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  DATOS DE DEMOSTRACIÓN
    // ══════════════════════════════════════════════════════════════════════════
    private void cargarDatosDemostracion() {
        Object[][] datos = {
            {"1001", "Caja",              "0,00", "Deudor"},
            {"1002", "Banco Nación",      "0,00", "Deudor"},
            {"2001", "Proveedores",       "0,00", "Acreedor"},
            {"2002", "Acreedores varios", "0,00", "Acreedor"},
            {"3001", "Capital Social",    "0,00", "Acreedor"},
        };
        for (Object[] fila : datos) modeloTabla.addRow(fila);
        actualizarLblPaginacion();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  MAIN
    // ══════════════════════════════════════════════════════════════════════════
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GestionCuentasPanel::new);
    }
}
