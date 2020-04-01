import 'package:flutter/material.dart';

void main() {
  runApp(MaterialApp(
      title: "Contador de Pessoas",
      home: Home()));
}

class Home extends StatefulWidget {
  @override
  _HomeState createState() => _HomeState();
}

class _HomeState extends State<Home> {

  int _peolple = 0;
  String _infoText = "Pode Entrar";
  void _changepeople(int delta){
    setState(() {
      _peolple += delta;

      if(_peolple < 0){
        _peolple = 0;
        _infoText = "Restaurante Vazio";
      }else if( _peolple <= 10){
        _infoText = "Pode entrar...";
      }
      else{
        _infoText = "Lotado!";
      }

    });

  }
  @override
  Widget build(BuildContext context) {
    return Stack(
      children: <Widget>[
        Image.asset(
          "images/restaurant.jpg",
          fit: BoxFit.cover,
          height: 1000.0,
          //width: 1000.0,
        ),
        Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              "Pessoas: $_peolple",
              style:
              TextStyle(color: Colors.white, fontWeight: FontWeight.bold),
            ),

            //Linha que contem os botões
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                Padding(
                  padding: EdgeInsets.all(10.0),
                  child: FlatButton(
                    child: Text("+1",
                        style:
                        TextStyle(fontSize: 40.0, color: Colors.white)),
                    onPressed: () {_changepeople(1);},
                  ),
                ),
                Padding(
                  padding: EdgeInsets.all(10.0),
                  child: FlatButton(
                    child: Text("-1",
                        style:
                        TextStyle(fontSize: 40.0, color: Colors.white)),
                    onPressed: () {_changepeople(-1);},
                  ),
                ),
              ],
            ),

            Text(_infoText,
                style: TextStyle(
                    color: Colors.white,
                    fontStyle: FontStyle.italic,
                    fontSize: 30.0)),
          ],
        )
      ],
    );
  }
}

