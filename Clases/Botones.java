package TeoriaTP;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Botones extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Botones(){
	}
	
	private JButton b1 = new JButton("Cargar Imagen");
	private JButton b2 = new JButton("Codificacion Fuente Sin Memoria");
	private JButton b3 = new JButton("Codificacion Fuente Con Memoria");
	private JButton b4 = new JButton("Entropia Fuente Sin Memoria");
	private JButton b5 = new JButton("Entropia Fuente Con Memoria");
	private JButton b6 = new JButton("Longitud Media Fuente Sin Memoria");
	private JButton b7 = new JButton("Longitud Media Fuente Con Memoria");
	private JButton b8 = new JButton("Salir");
	private JButton botonComprimirRun = new JButton("Comprimir con RunLenght");
	private JButton botonDescomprimirRun = new JButton("Descomprimir con RunLenght");
	private JButton botonComprimirHuffman = new JButton("Comprimir con Huffman");
	private JButton botonDescomprimirHuffman = new JButton("Descomprimir con Huffman");
	private JButton botonAnalizarCanal = new JButton("Analizar Canal Transmision");
	private JButton botonEnviarPorCanal = new JButton("Enviar Imagen Por Canal Transmision");
	private JButton botonEnviarDatos = new JButton("Enviar Datos Por Canal Transmision");
	
	private GridLayout ventana1 = new GridLayout(15, 1, 5, 5);
	
	private Imagen imagen = null;
	ArbolHuffman arbolSin = null;
	ArbolHuffman arbolCon = null;
	private Canal canal = null;
	
	public void showMsjAtencion(String comentario){
		JOptionPane.showMessageDialog(null, comentario, "ATENCION",  JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void showMsjError(String comentario){
		JOptionPane.showMessageDialog(null, comentario, "ERROR",  JOptionPane.ERROR_MESSAGE);
	}
	
	public void showMsjExito(String comentario){
		JOptionPane.showMessageDialog(null, new JLabel(comentario, JLabel.CENTER), "EXITO",  JOptionPane.PLAIN_MESSAGE);
	}
	
	
	
	public void main(){
		b1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				imagen = new Imagen();
				imagen.abrirImagen();
				if(imagen.estaCargada()){
					imagen.levantarImagen();
					arbolSin = new ArbolHuffman(imagen.getMatriz());
					arbolCon = new ArbolHuffmanOrden2(imagen.getMatriz());
					showMsjExito("Imagen cargada con exito");
				}else{
					showMsjAtencion("Imagen no cargada");
					imagen = null;
				}
			}
		});
		b2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			

				if(imagen != null){
					if(arbolSin.imprimirCodificacion()){
						showMsjExito("Codificacion fuente sin memoria generada con exito");
					}
				}else{
					showMsjError("IMAGEN NO CARGADA");
				}
			}			
				
		});
		b3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			if(imagen != null){
					if(arbolCon.imprimirCodificacion()){
						showMsjExito("Codificacion fuente con memoria generada con exito");
					}
			}else{
				showMsjError("IMAGEN NO CARGADA");}
			}		
		});
				
					
		b4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(arbolSin != null){
					float aux = arbolSin.calcularEntropia();
					JOptionPane.showMessageDialog(null, aux, "Entropia Fuente Sin Memoria", JOptionPane.INFORMATION_MESSAGE);
					
				}else{
					showMsjError("FUENTE SIN MEMORIA NO CODIFICADA");
				}
				}				
			});
		
		b5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(arbolCon != null){
					float entropia = arbolCon.calcularEntropia();
					JOptionPane.showMessageDialog(null, entropia, "Entropia Fuente Con Memoria", JOptionPane.INFORMATION_MESSAGE);
				}else{
				showMsjError("FUENTE CON MEMORIA NO CODIFICADA");
			}
		}});
		b6.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(arbolSin != null){
					float longSin = arbolSin.calcularLongitudMedia();
					JOptionPane.showMessageDialog(null, longSin, "Longitud Media Fuente Sin Memoria", JOptionPane.INFORMATION_MESSAGE);
				}
				else{
					showMsjError("FUENTE SIN MEMORIA NO CODIFICADA");
				}
		}});
		b7.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(arbolCon != null){
					float longCon = arbolCon.calcularLongitudMedia();
					JOptionPane.showMessageDialog(null, longCon, "Longitud Media Fuente Con Memoria", JOptionPane.INFORMATION_MESSAGE);	
				}else{
					showMsjError("FUENTE CON MEMORIA NO CODIFICADA");
					}
			
				
			}
		});
		b8.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			System.exit(0);
				
			}
		});
		
		botonComprimirRun.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(imagen != null){RunLenghtCoding comp = new RunLenghtCoding();
					comp.comprimir(imagen.getMatriz());
					showMsjAtencion("A continuacion debera seleccionar la ubicacion donde desea guardar el archivo comprimido");
					try {
						comp.guardarCompresion();
						showMsjExito("Compresion realizada con exito");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else{
					showMsjError("IMAGEN NO CARGADA");
				}
				
			}
		});
		
		botonDescomprimirRun.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showMsjAtencion("A continuacion debera seleccionar el archivo comprimido");
				Archivo arch = new Archivo();
				RunLenghtCoding comp = new RunLenghtCoding();
				try{
					
					DataInputStream aux = arch.abrir();
					if(aux != null){
						showMsjAtencion("A continuacion debera seleccionar la ubicacion donde desea guardar el archivo descomprimido");
						comp.descomprimir(aux);
					}else{
						showMsjError("Fallo la carga del archivo");
					}
				}catch(IOException ex){
					
				}
				
			}
		});
		
		botonComprimirHuffman.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(imagen != null){
					HuffmanCoding codif = new HuffmanCoding();
					codif.comprimir(imagen.getMatriz(), arbolSin);
					showMsjAtencion("A continuacion debera seleccionar la ubicacion donde desea guardar el archivo comprimido");
					try {
						codif.guardarCompresion();
						showMsjExito("Compresion realizada con exito");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else{
					showMsjError("IMAGEN NO CARGADA");
				}
				
			}
		});
		
		botonDescomprimirHuffman.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showMsjAtencion("A continuacion debera seleccionar el archivo comprimido");
				Archivo arch = new Archivo();
				HuffmanCoding codif = new HuffmanCoding();
				try{
					
					DataInputStream aux = arch.abrir();
					if(aux != null){
						showMsjAtencion("A continuacion debera seleccionar la ubicacion donde desea guardar el archivo descomprimido");
						codif.descomprimir(aux);
					}else{
						showMsjError("Fallo la carga del archivo");
					}
				}catch(IOException ex){
					
				}
				
			}
		});
		
		botonAnalizarCanal.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showMsjAtencion("A continuacion debera seleccionar la imagen original");
				Imagen imagenOriginal = new Imagen();
				imagenOriginal.abrirImagen();
				showMsjAtencion("A continuacion debera seleccionar la imagen luego de ser transmitida");
				Imagen imagenLlegada = new Imagen();
				imagenLlegada.abrirImagen();
				if(imagenOriginal.estaCargada() && imagenLlegada.estaCargada()){
					showMsjExito("Imagenes cargadas con exito");
					canal = new Canal();
					canal.generarCanal(imagenOriginal, imagenLlegada);
					JOptionPane.showMessageDialog(null, canal.mostarMatrizCanal(), "Matriz Canal", JOptionPane.INFORMATION_MESSAGE);	

					if(JOptionPane.showConfirmDialog(null, "¿Desea guardar la matriz?", "Guardar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
						Archivo archMatriz = new Archivo();
						archMatriz.guardarCadena(canal.mostarMatrizCanal());
					}
					JOptionPane.showMessageDialog(null, canal.calcularRuido(), "Ruido", JOptionPane.INFORMATION_MESSAGE);	
					JOptionPane.showMessageDialog(null, canal.calcularPerdida(), "Perdida", JOptionPane.INFORMATION_MESSAGE);	
					JOptionPane.showMessageDialog(null, canal.calcularInformacionMutua(), "Informacion Mutua", JOptionPane.INFORMATION_MESSAGE);	
					
					if(JOptionPane.showConfirmDialog(null, "¿Desea guardar la informacion?", "Guardar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
						String cadena = "Ruido: " + canal.calcularRuido() + "\r\n" +
										"Perdida: " + canal.calcularPerdida() + "\r\n" +
										"Informacion Mutua: " + canal.calcularInformacionMutua();
						Archivo arch = new Archivo();
						arch.guardarCadena(cadena);
					}
					
				}else{
					showMsjAtencion("Imagenes no cargadas");
					imagenOriginal = null;
					imagenLlegada = null;
				}
				
			}
		});

		botonEnviarPorCanal.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				if(canal != null){
				showMsjAtencion("A continuacion debera seleccionar la imagen a transmitir por el canal");
				Imagen imagenEnviar = new Imagen();
				imagenEnviar.abrirImagen();
	
					if(imagenEnviar.estaCargada()){
						showMsjExito("Imagen cargada con exito");
						
						Imagen imagenEnviada = canal.enviarImagen(imagenEnviar);
						if(JOptionPane.showConfirmDialog(null, "¿Desea guardar la imagen transmitida?", "Guardar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
							imagenEnviada.guardar();
						}

						Canal nuevoCanal = new Canal();
						nuevoCanal.analizarCanalConImagenEnviada(imagenEnviar, imagenEnviada, canal.getTonos());
						
						JOptionPane.showMessageDialog(null, nuevoCanal.mostarMatrizCanal(), "Matriz Canal", JOptionPane.INFORMATION_MESSAGE);	
						
						if(JOptionPane.showConfirmDialog(null, "¿Desea guardar la matriz?", "Guardar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
							Archivo archMatriz2 = new Archivo();
							archMatriz2.guardarCadena(nuevoCanal.mostarMatrizCanal());
						}
						
						JOptionPane.showMessageDialog(null, nuevoCanal.calcularRuido(), "Ruido", JOptionPane.INFORMATION_MESSAGE);	
						JOptionPane.showMessageDialog(null, nuevoCanal.calcularPerdida(), "Perdida", JOptionPane.INFORMATION_MESSAGE);	
						JOptionPane.showMessageDialog(null, nuevoCanal.calcularInformacionMutua(), "Informacion Mutua", JOptionPane.INFORMATION_MESSAGE);	
						
						if(JOptionPane.showConfirmDialog(null, "¿Desea guardar la informacion?", "Guardar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
							String cadena = "Ruido: " + nuevoCanal.calcularRuido() + "\r\n" +
											"Perdida: " + nuevoCanal.calcularPerdida() + "\r\n" +
											"Informacion Mutua: " + nuevoCanal.calcularInformacionMutua();
							Archivo arch = new Archivo();
							arch.guardarCadena(cadena);
						}
					}else{
						showMsjAtencion("Imagen no cargada");
						imagenEnviar = null;
					}
				}else{
					showMsjAtencion("Canal no analizado");
				}
				
			}
		});
		
		botonEnviarDatos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int cantDatos = 10;
				int cantIteraciones = 6;
				ArrayList<NodoInformacion> listaResultados = new ArrayList<NodoInformacion>();
					if(canal != null){
						for(int i=0; i<cantIteraciones; i++){
							int cantActual = cantDatos;
							for(int j=0; j<i; j++){
								cantActual = cantActual*cantDatos;
							}
							listaResultados.add(canal.enviarDatosAleatorios(cantActual));
						}
							showMsjAtencion("A continuacion debera seleccionar la ubicacion donde desea guardar el analisis de los datos");
							Archivo arch = new Archivo();
							arch.guardarComoTextoNodoInformacion(listaResultados);	
							showMsjExito("Datos enviados con exito");
							
					}else{
						showMsjAtencion("Canal no analizado");
					}
				
			}
		});

		getContentPane().add(b1, BorderLayout.NORTH);
		getContentPane().add(b2,BorderLayout.SOUTH);
		getContentPane().add(b3, BorderLayout.EAST);
		getContentPane().add(b4, BorderLayout.WEST);
		getContentPane().add(b5,BorderLayout.SOUTH);
		getContentPane().add(b6, BorderLayout.EAST);
		getContentPane().add(b7, BorderLayout.WEST);
		getContentPane().add(botonComprimirHuffman, BorderLayout.WEST);
		getContentPane().add(botonDescomprimirHuffman, BorderLayout.WEST);
		getContentPane().add(botonComprimirRun, BorderLayout.WEST);
		getContentPane().add(botonDescomprimirRun, BorderLayout.WEST);
		getContentPane().add(botonAnalizarCanal, BorderLayout.WEST);
		getContentPane().add(botonEnviarPorCanal, BorderLayout.WEST);
		getContentPane().add(botonEnviarDatos, BorderLayout.WEST);
		getContentPane().add(b8, BorderLayout.WEST);
		
		
		getContentPane().setLayout(ventana1);
		setSize(500, 550);
		
		setLocationRelativeTo(null);
		setTitle("Teoria de la Informacion");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
		
	public static void main(String[] args){
		Botones b = new Botones();
		b.main();
	}
	
	
}
