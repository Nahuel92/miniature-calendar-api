'use strict';

class RandomDatePicturesButton extends React.Component {
    constructor(props) {
        super(props);
        this.state = {liked: false, items: [], error: null, loaded: false};
    }

    fetch() {
        fetch("http://localhost:8080/picture?random=true")
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
                this.state.error.error,
                e('button',
                    {onClick: () => this.setState({liked: false, error: null})},
                    'Hide Pictures from Date'
                )
            );
        }

        if (!this.state.error && this.state.liked && !this.state.loaded) {
            return e('div', {}, "Loading...");
        }

        if (!this.state.error && this.state.liked && this.state.loaded) {
            const g = this.state.items.map(function (d) {
                return [e('a', {href: d.url, key: d.url}, d.url),
                    e('img',
                        {src: `data:image/jpg;base64,${d.picture}`, key: d.url + d.url},
                        null
                    )];
            });

            const cnt = document.querySelector('#content');
            ReactDOM.render(g, cnt);

            return e('div',
                {},
                e('button',
                    {onClick: () => {this.setState({liked: false, loaded: false, items: []});
                            const cnt = document.querySelector('#content');
                            ReactDOM.render(e('div', {}), cnt);}
                        },
                    'Hide Random Pictures of the Day')
            );
        }

        return e(
            'button',
            {
                onClick: () => {
                    this.setState({liked: true});
                    this.fetch()
                }
            },
            'Show Random Pictures of the Day'
        );
    }
}

const dom2Container = document.querySelector('#random_pictures_button_container');
ReactDOM.render(e(RandomDatePicturesButton), dom2Container);
