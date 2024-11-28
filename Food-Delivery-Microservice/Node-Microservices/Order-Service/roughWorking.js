class Animal {
    makeSound() {
        console.log("animal");

    }
}
class dog extends Animal {
    makeSound() {
        console.log("Barks");
        super.makeSound();

    }
}
let d = new dog()
d.makeSound();
