package elements;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Cashbox {

    private int x;
    private int y;
    private int profit; //заработок за все время
    private int balance;
    private int criticalLevel; //between 10000 nd 100000
    private boolean status = true;

    public void setBalance(int payment){
        this.balance += payment;
        this.profit += payment;
    }

    private void checkCashbox(){
        if (balance >= criticalLevel && this.status == true)
        {
            this.status = false;
            //callCollectorCashbox()
            this.status = true;
            this.balance = 0;
        }
    }
}
