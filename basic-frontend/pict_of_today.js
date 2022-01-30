'use strict';

const e = React.createElement;

class PicturesOfTodayButton extends React.Component {
  constructor(props) {
    super(props);
    this.state = { liked: false, items: [], error: null, loaded: false };
  }

  fetch() {
    fetch("http://localhost:8080/picture?random=false")
        .then((res) => {
          if (res.ok) {
            return res.json().then(result => {
              this.setState({
                items: result,
                loaded: true
              });
            })
          }

          return res.json().then(error => {
            this.setState({
              loaded: true,
              error
            });
          })
        });
  }

  render() {
    if (this.state.error) {
      return e('div',
        {},
        this.state.error,
        e('button',
          { onClick: () => this.setState({ liked: false }) },
          'Hide Pictures of the Day'
        )
      );
    }

    if (!this.state.error && this.state.liked && this.state.loaded) {
      const g = this.state.items.map(function(d) {
          return [e('a', {href: d.url, key: d.url}, d.url),
          e('img',
            { src: `data:image/jpg;base64,${d.picture}`, key: d.url+d.url },
            null
          )];
      });

      return e('div',
        {},
        g,
        e('button',
      { onClick: () => this.setState({ liked: false }) },
      'Hide Pictures of the Day')
      );
    }

    return e(
      'button',
      { onClick: () => { this.setState({ liked: true }); this.fetch() } },
      'Show Pictures of the Day'
    );
  }
}

const domContainer = document.querySelector('#pictures_of_today_button_container');
ReactDOM.render(e(PicturesOfTodayButton), domContainer);