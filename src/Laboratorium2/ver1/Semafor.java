package Laboratorium2.ver1;

class Semafor {

    private boolean _stan = true;
    private int _czeka;

    public Semafor(int _czeka) {
        this._czeka = _czeka;
    }


    // Zajmij
    public synchronized void P() {
        while (!_stan) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        _czeka--;
        if (_czeka < 1)
            _stan = false;
    }

    // Zwolnij
    public synchronized void V() {
        _czeka++;
        _stan = true;
        notify();
    }
}

