'use strict';

class PicturesFromDateButton extends React.Component {
    constructor(props) {
        super(props);
        this.state = {liked: false, items: [], error: null, date: null, loaded: false};
    }

    fetchFromDate() {
        const date = (this.state.date + '').replaceAll('-', '').substring(2);

        fetch(`http://localhost:8080/picture/` + date)
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
                    {onClick: () => {this.setState({liked: false, loaded: false, date: null});
                            const cnt = document.querySelector('#content');
                            ReactDOM.render(e('div', {}), cnt);
                    }},
                    'Hide Pictures from Date')
            );
        }

        return e('div', {},
            e('div', {},
                e('button',
                    {
                        disabled: !this.state.date || new Date(this.state.date) > new Date(),
                        onClick: () => {
                            this.setState({liked: true});
                            this.fetchFromDate();
                        }, key: 'pictFromDate'
                    },
                    'Show Pictures from Date'
                )
            ),
            e('div', {},
                e('input',
                    {
                        placeholder: 'YYMMDD',
                        type: 'date',
                        key: 'input',
                        min: '2011-04-20',
                        max: new Date().toISOString().slice(0,10),
                        onChange: e => this.setState({date: e.target.value})
                    },
                    null
                )
            )
        );
    }
}

const dom3Container = document.querySelector('#pictures_from_date_button_container');
ReactDOM.render(e(PicturesFromDateButton), dom3Container);
