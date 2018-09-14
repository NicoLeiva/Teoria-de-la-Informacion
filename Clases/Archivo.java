package TeoriaTP;


import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Archivo {

	private  File archivo;
	
	public Archivo(){
	}
		
	public DataInputStream abrir(){
		DataInputStream archivoInput = null;
		try{			
			JFileChooser file = new JFileChooser();
			file.showOpenDialog(file);
			this.archivo = file.getSelectedFile();

			if(this.archivo != null){     
				  Path aux = this.archivo.toPath();
				  InputStream input = new FileInputStream(aux.toString());
				  archivoInput = new DataInputStream(input); 
			    }    
			   }
			   catch(IOException ex)
			   {
			     JOptionPane.showMessageDialog(null,ex+"" +
			           "\nNo se ha encontrado el archivo",
			                 "ADVERTENCIA!!!",JOptionPane.WARNING_MESSAGE);
			    }
		return archivoInput;
	}
	
	public boolean guardarComoTexto(ArrayList<NodoArbolHuffman> listaGuardar){
		
		JFileChooser file=new JFileChooser();
		file.showSaveDialog(file);
		File abre=file.getSelectedFile();
		if(abre!=null){
			Path aux = abre.toPath();
			Charset utf8 = StandardCharsets.UTF_8;
			try(BufferedWriter w = Files.newBufferedWriter(aux,utf8)){
				for ( NodoArbolHuffman t : listaGuardar) {
					w.write("Nombre: "+ t.getNombre() +"\t"+" Codificacion:"+  t.getCodificacion() + "\t" +"  Longitud:" + t.getLongitud() +
							"\t"+ "Frecuencia:"+t.getFrecuencia()+"\r\n");
				}
				return true;
			}catch(Exception e){
		}
		}else{
			JOptionPane.showMessageDialog(null,
			         "No se ha encontrado el archivo",
			          "Error",JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}
	
	public OutputStream guardar(){
		try{
			JFileChooser file=new JFileChooser();
			file.showSaveDialog(file);
			this.archivo = file.getSelectedFile();
			if(this.archivo != null){     
			  Path aux = this.archivo.toPath();
			  OutputStream output = new FileOutputStream(aux.toString()+".txt");
			  DataOutputStream archivoSalida = new DataOutputStream(output);
			  return archivoSalida;
			  
		    }else{
				JOptionPane.showMessageDialog(null,
				         "No se ha encontrado el archivo",
				          "Error",JOptionPane.ERROR_MESSAGE);
			}    
		}catch(IOException ex){
		     JOptionPane.showMessageDialog(null,ex+"" +
		           "\nNo se ha encontrado el archivo",
		                 "ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
		    }
		return null;
	}
	
	
	
public boolean guardarComoTextoNodoInformacion(ArrayList<NodoInformacion> listaGuardar){
		
		JFileChooser file=new JFileChooser();
		file.showSaveDialog(file);
		File abre=file.getSelectedFile();
		if(abre!=null){
			Path aux = abre.toPath();
			Charset utf8 = StandardCharsets.UTF_8;
			try(BufferedWriter w = Files.newBufferedWriter(aux,utf8)){
				for (NodoInformacion t : listaGuardar) {
					w.write("Cantidad Datos Enviados: "+ t.getCantidadDatosEnviados() +"\t"+" Ruido:"+  t.getRuido() + "\t" +"  Perdida:" + t.getPerdida() +
							"\t"+ "Informacion Mutua:"+t.getInformacionMutua()+"\r\n");
				}
				return true;
			}catch(Exception e){
		}
		}else{
			JOptionPane.showMessageDialog(null,
			         "No se ha encontrado el archivo",
			          "Error",JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}

public boolean guardarCadena(String guardar){
	
	JFileChooser file=new JFileChooser();
	file.showSaveDialog(file);
	File abre=file.getSelectedFile();
	if(abre!=null){
		Path aux = abre.toPath();
		Charset utf8 = StandardCharsets.UTF_8;
		try(BufferedWriter w = Files.newBufferedWriter(aux,utf8)){
				w.write(guardar);
	
			return true;
		}catch(Exception e){
	}
	}else{
		JOptionPane.showMessageDialog(null,
		         "No se ha encontrado el archivo",
		          "Error",JOptionPane.ERROR_MESSAGE);
	}
	return false;
}
}
