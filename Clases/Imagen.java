package TeoriaTP;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class Imagen{
	private int matriz[][];
	private BufferedImage imagenL;
	
	public Imagen(){
		this.matriz = null;
		this.imagenL = null;
	}
	
	public boolean estaCargada(){
		return (this.imagenL != null);
	}
	
	public void abrirImagen(){
		try{			
			JFileChooser file=new JFileChooser();
			   file.showOpenDialog(file);
			   File abre=file.getSelectedFile();
			   Path aux = abre.toPath();
			   InputStream input = new FileInputStream(aux.toString());
			   ImageInputStream imageInput = ImageIO.createImageInputStream(input);
			   imagenL = ImageIO.read(imageInput);
		}catch (Exception e){}
	}
	
	public int[][] getMatriz(){
		return this.matriz;
		
	}
	public void setMatriz(int mat[][]){
		this.matriz=mat;
	}

	public void levantarImagen(){
			int col = this.imagenL.getHeight();
			int fila= this.imagenL.getWidth();
			this.matriz = new int [fila][col];
			int i=0;
			int j=0;
			while(fila>i){
				while(col>j){
					int pixel = this.imagenL.getRGB(i, j);	
					Color c = new Color (pixel);
					int valR = c.getRed();
					this.matriz[i][j]=valR;
					j++;
					}
				i++;
				j=0;
				}	
	}
	public void imprimirMatriz(){
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[0].length; j++) {
				System.out.print(matriz[i][j] + " ");
			}
		}
	}

	public void generarImagen(int[][] matrizImagen){
	
		imagenL = new BufferedImage(matrizImagen.length, matrizImagen[0].length, 10);
			
		for (int fil = 0; fil < matrizImagen.length; fil++) {
			for (int col = 0; col < matrizImagen[0].length; col++){
				int rgb = new Color(matrizImagen[fil][col],matrizImagen[fil][col],matrizImagen[fil][col]).getRGB();
				imagenL.setRGB(fil, col, rgb);
			}
		}					
	}

	public void guardar(){
		try{		
			JFileChooser file=new JFileChooser();
			file.showSaveDialog(file);
			File abre = file.getSelectedFile();
			if(abre!= null){
				Path aux = abre.toPath();
				OutputStream output = new FileOutputStream(aux.toString()+".bmp");
				ImageOutputStream imageOutput = ImageIO.createImageOutputStream(output);
				ImageIO.write(imagenL, "bmp", imageOutput);
				imageOutput.close();
				JOptionPane.showMessageDialog(null,
				         "Imagen guardada con exito",
				          "EXITO",JOptionPane.PLAIN_MESSAGE);
			}else{
				JOptionPane.showMessageDialog(null,
				         "No se ha encontrado el archivo",
				          "Error",JOptionPane.ERROR_MESSAGE);
			}   
		}catch (Exception e){}
	}

}
		
		
		

	
