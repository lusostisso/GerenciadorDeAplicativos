import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.MouseInputAdapter;

/*
*@Author Luana Sostisso
 */
public class App {
    private CatalogoCliente catCliente;
    private CatalogoClientesViewModel catClienteVM;
    private CatalogoAplicativos catApps;
    private CatalogoAplicativosViewModel catAppsVM;
    private GerenciadorDeAssinaturas gerenciadorDeAssinaturas;
    private GerenciadorDeAssinaturasViewModelMostraApps gerenciadorDeAssinaturasViewModel;
    private GerenciadorDeAssinaturasViewModelMostraClientes gerenciadorDeAssinaturasViewModelMostraClientes;
    private ValorASerCobradoViewModel valorASerCobradoViewModel;
    private JTextField tfCpf;
    private JTextField tfNomeCliente;
    private JTextField tfEmail;
    private JTextField tfCodigo;
    private JTextField tfNome;
    private JTextField tfPreco;
    private JComboBox<Aplicativo.SO> cbSo;
    private JButton btAdd;


    public App(){
        catApps = new CatalogoAplicativos();
        catApps.loadDataFromFile();
        catCliente = new CatalogoCliente();
        catCliente.loadDataFromFile();
        gerenciadorDeAssinaturas = new GerenciadorDeAssinaturas();
        gerenciadorDeAssinaturas.loadDataFromFile(catCliente, catApps);
    }
    public void criaJanelaInicial(){
        JFrame frame = new JFrame("Pagina Inicial");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = frame.getContentPane();
        JPanel linha= new JPanel(new FlowLayout(FlowLayout.CENTER));

        JPanel linha1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btSave1 = new JButton("Cliente");
        btSave1.addActionListener(e -> {frame.dispose(); criaJanelaCliente();});
        linha1.add(btSave1);

        JButton btSave2 = new JButton("Aplicativo");
        btSave2.addActionListener(e -> {frame.dispose(); criaJanelaApp();});
        linha1.add(btSave2);

        JPanel linha2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btSave3 = new JButton("Listar Cobrança Dos Clientes");
        btSave3.addActionListener(e -> {frame.dispose(); criaJanelaValorASerCobradoDeClientes();});
        linha2.add(btSave3);

        JButton btSave4 = new JButton("Faturamento Empresa");
        btSave4.addActionListener(e -> {
            frame.dispose();
            criaJanelaFaturamento();
        });
        linha2.add(btSave4);

        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        contentPane.add(linha);
        contentPane.add(linha1);
        contentPane.add(linha2);

        frame.setBounds(550, 550, 550, 550);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void criaJanelaApp(){
        catAppsVM = new CatalogoAplicativosViewModel(catApps);
        JTable tabela = new JTable(catAppsVM);
        tabela.setFillsViewportHeight(true);

        JFrame frame = new JFrame("Gestão de aplicativos");


        tabela.addMouseListener(new MouseInputAdapter() {
            public void mouseClicked(MouseEvent e) {
                int colunaClicada = tabela.columnAtPoint(e.getPoint());
                int linhaClicada= tabela.rowAtPoint(e.getPoint());
                if(colunaClicada==4){
                    frame.dispose();
                    for (Aplicativo app: catApps.getAplicativos()){
                        if(linhaClicada==catApps.indexOf(app)){
                            criaJanelaAssinaturaMostraClientes(app);
                        }
                    }
                }
            }
        });


        JMenuBar menuBar = new JMenuBar();
        JButton sairItem = new JButton("Voltar");
        JButton faturamento = new JButton("Faturamento do Aplicativo");
        faturamento.setEnabled(false);
        sairItem.addActionListener(e -> {frame.dispose(); criaJanelaInicial();});

        menuBar.add(sairItem);
        menuBar.add(faturamento);
        frame.setJMenuBar(menuBar);

        JPanel linha1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JScrollPane scrollPane = new JScrollPane(tabela,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        linha1.add(scrollPane);

        JPanel jpNovoApp = criaPainelNovoApp();

        JPanel linha3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btSave = new JButton("Salvar dados");
        btSave.addActionListener(e->catApps.saveDataToFile());
        linha3.add(btSave);


        final Aplicativo[] aplicativoClicado = new Aplicativo[1];
        tabela.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = tabela.getSelectedRow();
            faturamento.setEnabled(selectedRow != -1);
            if (selectedRow != -1) {
                aplicativoClicado[0] = catApps.getAplicativos().get(selectedRow);
            }
        });

        faturamento.addActionListener(e -> {
            frame.dispose();
            JOptionPane.showMessageDialog(null, "Faturamento do APP: " +
                    gerenciadorDeAssinaturas.getFaturamentoAplicativoEspecifico(aplicativoClicado[0]), "Informação", JOptionPane.INFORMATION_MESSAGE);
            criaJanelaApp();
        });

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        contentPane.add(linha1);
        contentPane.add(jpNovoApp);
        contentPane.add(linha3);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(550, 550, 550, 550);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    public void criaJanelaFaturamento () {
        JFrame frame = new JFrame("Faturamento Empresa");
        JButton sairItem = new JButton("Voltar");
        sairItem.addActionListener(e -> {frame.dispose();criaJanelaInicial();});
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(sairItem);
        frame.setJMenuBar(menuBar);

        double faturamentoIOS = gerenciadorDeAssinaturas.getFaturamentoAplicativosIOS();
        double faturamentoAndroid = gerenciadorDeAssinaturas.getFaturamentoAplicativosAndroid();
        double faturamentoTotal = faturamentoIOS + faturamentoAndroid;

        JLabel fatIOS = new JLabel("FATURAMENTO IOS: R$ "+ faturamentoIOS, SwingConstants.CENTER);
        JLabel fatAndr = new JLabel("FATURAMENTO ANDROID: R$ " + faturamentoAndroid, SwingConstants.CENTER);
        JLabel fatTotal = new JLabel("FATURAMENTO TOTAL: R$ " + faturamentoTotal, SwingConstants.CENTER);

        JPanel linha1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel linha2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel linha3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        linha1.add(fatIOS);
        linha2.add(fatAndr);
        linha3.add(fatTotal);


        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        contentPane.add(linha1);
        contentPane.add(linha2);
        contentPane.add(linha3);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(550, 550, 550, 550);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public void criaJanelaValorASerCobradoDeClientes () {
        valorASerCobradoViewModel = new ValorASerCobradoViewModel(gerenciadorDeAssinaturas);
        JTable tabela = new JTable(valorASerCobradoViewModel);
        tabela.setFillsViewportHeight(true);
        JFrame frame = new JFrame("Gestão de Contas Cliente");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JMenuBar menuBar = new JMenuBar();
        JButton sairItem = new JButton("Voltar");

        sairItem.addActionListener(e -> {frame.dispose(); criaJanelaInicial();});

        menuBar.add(sairItem);
        frame.setJMenuBar(menuBar);

        JPanel linha1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JScrollPane scrollPane = new JScrollPane(tabela,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        linha1.add(scrollPane);

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        contentPane.add(linha1);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(550, 550, 550, 550);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void criaJanelaCliente(){
        catClienteVM = new CatalogoClientesViewModel(catCliente);
        JTable tabela = new JTable(catClienteVM);
        tabela.setFillsViewportHeight(true);
        JFrame frame = new JFrame("Gestão de clientes");

        JMenuBar menuBar = new JMenuBar();
        JButton sairItem = new JButton("Voltar");

        sairItem.addActionListener(e -> {frame.dispose(); criaJanelaInicial();});

        menuBar.add(sairItem);
        frame.setJMenuBar(menuBar);

        JPanel linha1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JScrollPane scrollPane = new JScrollPane(tabela,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        linha1.add(scrollPane);

        JPanel jpNovoCliente = criaPainelNovoCliente();

        JPanel linha3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btSave = new JButton("Salvar dados");
        btSave.addActionListener(e->catCliente.saveDataToFile());
        linha3.add(btSave);

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        contentPane.add(linha1);
        contentPane.add(jpNovoCliente);
        contentPane.add(linha3);

        tabela.addMouseListener(new MouseInputAdapter() {
            public void mouseClicked(MouseEvent e) {
                int colunaClicada = tabela.columnAtPoint(e.getPoint());
                int linhaClicada= tabela.rowAtPoint(e.getPoint());
                if(colunaClicada==3){
                    frame.dispose();
                    for (Cliente c: catCliente.getClientes()){
                        if(linhaClicada==catCliente.indexOf(c)){
                            criaJanelaAssinaturaMostraApps(c);
                            break;
                        }
                    }
                }
            }
        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(550, 550, 550, 550);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void criaJanelaAssinaturaMostraApps(Cliente cliente){
        AplicativosDeUmCliente appsDeUmCliente = gerenciadorDeAssinaturas.getAplicativoDeUmClienteEspecifico(cliente);
        if(appsDeUmCliente!=null){
            gerenciadorDeAssinaturasViewModel = new GerenciadorDeAssinaturasViewModelMostraApps(appsDeUmCliente);

            JLabel clienteDados = new JLabel("CLIENTE: " + cliente.getNome() + " CPF: " + cliente.getCpf(), SwingConstants.CENTER);

            JTable tabela = new JTable(gerenciadorDeAssinaturasViewModel);
            tabela.setFillsViewportHeight(true);

            JFrame frame = new JFrame("Assinaturas");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JMenuBar menuBar = new JMenuBar();
            JButton sairItem = new JButton("Voltar");
            JButton novaAssinatura = new JButton("Nova Assinatura");

            sairItem.addActionListener(e -> {frame.dispose(); criaJanelaCliente();});

            novaAssinatura.addActionListener(e -> {
                frame.dispose();
                if (cliente!= null) {
                    adicionaAssinatura(cliente);
                }
            });

            tabela.addMouseListener(new MouseInputAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int colunaClicada = tabela.columnAtPoint(e.getPoint());
                    int linhaClicada= tabela.rowAtPoint(e.getPoint());
                    if(colunaClicada==6){
                        frame.dispose();
                        List<Aplicativo> aux = gerenciadorDeAssinaturas.getListaDeAplicativosDeUmClienteEspecifico(cliente);
                        for (Aplicativo app: aux){
                            if(linhaClicada==aux.indexOf(app)){
                                gerenciadorDeAssinaturas.trocaStatusDeUmAplicativoDeUmCliente(cliente,app);
                                gerenciadorDeAssinaturas.saveDataToFile();
                                criaJanelaAssinaturaMostraApps(cliente);
                                break;
                            }
                        }
                    }
                }
            });

            menuBar.add(sairItem);
            menuBar.add(novaAssinatura);
            frame.setJMenuBar(menuBar);


            JPanel linha1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JScrollPane scrollPane = new JScrollPane(tabela,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            linha1.add(clienteDados);
            linha1.add(scrollPane);

            Container contentPane = frame.getContentPane();
            contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
            contentPane.add(linha1);

            frame.setBounds(550, 550, 550, 550);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Esse Cliente Não Possui Assinaturas", "Informação", JOptionPane.INFORMATION_MESSAGE);
            adicionaAssinatura(cliente);
        }
    }

    public void criaJanelaAssinaturaMostraClientes(Aplicativo app){
        List <AplicativosDeUmCliente> lista = gerenciadorDeAssinaturas.getClientesDoAplicativo(app);
        if (lista.size()>0) {
            gerenciadorDeAssinaturasViewModelMostraClientes = new GerenciadorDeAssinaturasViewModelMostraClientes(lista, app);

            JLabel dadosApp = new JLabel("APP: " + app.getNome() + " CODIGO: " + app.getCodigo(), SwingConstants.CENTER);

            JTable tabela = new JTable(gerenciadorDeAssinaturasViewModelMostraClientes);
            tabela.setFillsViewportHeight(true);

            JFrame frame = new JFrame("Assinaturas");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JMenuBar menuBar = new JMenuBar();
            JButton sairItem = new JButton("Voltar");
            sairItem.addActionListener(e -> {
                frame.dispose();
                criaJanelaApp();
            });
            menuBar.add(sairItem);
            frame.setJMenuBar(menuBar);


            JPanel linha1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JScrollPane scrollPane = new JScrollPane(tabela, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            linha1.add(dadosApp);
            linha1.add(scrollPane);

            Container contentPane = frame.getContentPane();
            contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
            contentPane.add(linha1);

            frame.setBounds(550, 550, 550, 550);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(null, "Esse Aplicativo Não Possui Assinaturas", "Informação", JOptionPane.INFORMATION_MESSAGE);
            criaJanelaApp();
        }
    }
    public JPanel criaPainelNovoApp(){
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel,BoxLayout.PAGE_AXIS));

        JPanel linha1 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        linha1.add(new JLabel("Codigo"));
        tfCodigo = new JTextField(10);
        linha1.add(tfCodigo);
        linha1.add(new JLabel("Nome"));
        tfNome = new JTextField(20);
        linha1.add(tfNome);
        JPanel linha2 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        linha2.add(new JLabel("Preco"));
        tfPreco = new JTextField(10);
        linha2.add(tfPreco);
        linha2.add(new JLabel("Sist. Oper."));
        cbSo = new JComboBox<>(Aplicativo.SO.values());
        linha2.add(cbSo);
        btAdd = new JButton("Novo App");
        btAdd.addActionListener(e->adicionaApp());
        linha2.add(btAdd);

        painel.add(linha1);
        painel.add(linha2);
        return painel;
    }
    public JPanel criaPainelNovoCliente(){
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.PAGE_AXIS));

        JPanel linha1 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        linha1.add(new JLabel("CPF"));
        tfCpf = new JTextField(10);
        linha1.add(tfCpf);
        linha1.add(new JLabel("Nome"));
        tfNomeCliente = new JTextField(20);
        linha1.add(tfNomeCliente);

        JPanel linha2 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        linha2.add(new JLabel("Email"));
        tfEmail = new JTextField(20);
        linha2.add(tfEmail);
        btAdd = new JButton("Novo Cliente");
        btAdd.addActionListener(e -> adicionaCliente());
        linha2.add(btAdd);


        painel.add(linha1);
        painel.add(linha2);
        return painel;
    }
    public void adicionaAssinatura(Cliente clienteClicado){
        try {
            JLabel dadosCliente = new JLabel("Cliente: " + clienteClicado.getNome() + "; CPF: " + clienteClicado.getCpf(), SwingConstants.CENTER);

            List<Aplicativo> apps = catApps.getAplicativos();
            List <String> nomeApps = new ArrayList<>();

            for(Aplicativo app: apps){
                nomeApps.add("Código:"+app.getCodigo()+"; Nome:"+app.getNome()+"; Preco:"+ app.getPreco()+"; SO:"+ app.getSo());
            }

            JComboBox<String> cbSo = new JComboBox<>(nomeApps.toArray(new String[0]));

            JFrame frame = new JFrame("Nova Assinatura");
            JMenuBar menuBar = new JMenuBar();
            JButton sairItem = new JButton("Voltar");
            JButton cadastrarNovaAssinatura = new JButton("Cadastrar Nova Assinatura");

            sairItem.addActionListener(e -> {
                frame.dispose();
                if(nomeApps.size()== apps.size()){
                    criaJanelaCliente();
                }
                else{
                    criaJanelaAssinaturaMostraApps(clienteClicado);
                }
            });
            cadastrarNovaAssinatura.addActionListener(e -> {
                Aplicativo aplicativoSelecionado=null;
                frame.dispose();
                String aplicativo = (String) cbSo.getSelectedItem();
                int count =0;
                for (Aplicativo a:apps){
                    if(aplicativo.contains(String.valueOf(a.getCodigo()))){
                        aplicativoSelecionado = apps.get(count);
                    }
                    count++;
                }
                AplicativosDeUmCliente clientePossuiAssinaturas = gerenciadorDeAssinaturas.getAplicativoDeUmClienteEspecifico(clienteClicado);
                LocalDate hoje = LocalDate.now();
                String dataAtual = hoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                if(clientePossuiAssinaturas!=null){
                    clientePossuiAssinaturas.add(new Assinatura(StatusAssinatura.ATIVO, aplicativoSelecionado, dataAtual));
                } else {
                    gerenciadorDeAssinaturas.add(clienteClicado, new Assinatura(StatusAssinatura.ATIVO,aplicativoSelecionado, dataAtual));
                }
                gerenciadorDeAssinaturas.saveDataToFile();
                criaJanelaAssinaturaMostraApps(clienteClicado);
            });

            menuBar.add(sairItem);
            menuBar.add(dadosCliente);
            frame.setJMenuBar(menuBar);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setBounds(550,550,550,550);
            frame.setLayout(new FlowLayout());
            frame.add(cbSo);
            frame.add(cadastrarNovaAssinatura);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Algo deu errado!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void adicionaApp(){
        try {
            int codigo = Integer.parseInt(tfCodigo.getText());
            String nome = tfNome.getText();
            double preco = Double.parseDouble(tfPreco.getText());
            Aplicativo.SO so = (Aplicativo.SO)cbSo.getSelectedItem();

            if((nome.trim().isEmpty()) || codigo==0|| preco==0.0){
                throw new Exception();
            }
            else if(catApps.getAplicativo(codigo)!=null){
                JOptionPane.showMessageDialog(null, "App já existente! ", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            else if(!verificaTresDigitos(codigo)){
                JOptionPane.showMessageDialog(null, "Codigo aceita ate 3 digitos ", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            else{
                Aplicativo novo = new Aplicativo(codigo, nome, preco, so);
                catApps.cadastra(novo);
                catAppsVM.fireTableDataChanged();
                tfCodigo.setText("");
                tfPreco.setText("");
                tfNome.setText("");
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Dados incorretos ou campo vazio! Por favor, verifique os dados inseridos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }
    public void adicionaCliente(){
        try {
            String nome = tfNomeCliente.getText();
            String cpf = tfCpf.getText();
            String email = tfEmail.getText();

            if((nome.trim().isEmpty()) || (cpf.trim().isEmpty())|| (email.trim().isEmpty())){
                throw new IllegalArgumentException();
            }
            else if(catCliente.getCliente(tfCpf.getText())!= null){
                throw new NumberFormatException();
            }
            else if(!validarEmail(email)|| !validarFormatoCPF(cpf)){
                throw new Exception();
            }
            else{
                Cliente novo = new Cliente(nome, cpf, email);
                catCliente.cadastra(novo);
                catClienteVM.fireTableDataChanged();
                tfCpf.setText("");
                tfEmail.setText("");
                tfNomeCliente.setText("");
            }
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Cliente já existente! ", "Erro", JOptionPane.ERROR_MESSAGE);
        }catch(IllegalArgumentException e){
            JOptionPane.showMessageDialog(null, "Campos Vazio! Por favor, insira todos os dados.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Dados incorretos! Por favor, verifique os dados inseridos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    public static boolean validarEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
    }
    public static boolean validarFormatoCPF(String cpf) {
        return cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");
    }
    public static boolean verificaTresDigitos(int numero) {
        return numero >= 0 && numero <= 999;
    }

    public static void main(String[] args) throws Exception {
        App app = new App();
        app.criaJanelaInicial();
    }

}