package com.annotation;


@AnimalDef(name = "cat")
@AnimalDef(name = "dog")
@AnimalDef(name = "tiger")
public class AnimalsZooDemo {

	public static void main(String[] args) {
		AnimalDef[] animals = AnimalsZooDemo.class.getAnnotationsByType(AnimalDef.class);
		for (AnimalDef animal : animals) {
			System.out.println("animal name = " + animal.name());
		}
	}
}
