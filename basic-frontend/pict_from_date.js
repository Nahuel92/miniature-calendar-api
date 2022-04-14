'use strict';

class PicturesFromDateButton extends React.Component {
    constructor(props) {
        super(props);
        this.hideText = 'Hide Pictures from Date';
        this.state = {items: [], error: null, date: null, isPressed: false, loaded: false};
    }

    fetchFromDate() {
        const date = (this.state.date + '').replaceAll('-', '').substring(2);
        fetchData(this, date);
    }

    render() {
        return new TemplateComponent(this).render();
    }

    showButton() {
        return e('div', {},
            e('div', {},
                e('button',
                    {
                        disabled: !this.state.date || new Date(this.state.date) > new Date(),
                        onClick: () => {
                            this.setState({isPressed: true});
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
                        max: new Date().toISOString().slice(0, 10),
                        onChange: e => this.setState({date: e.target.value})
                    }
                )
            )
        );
    }

    displayData() {
        return e('div',
            {},
            e('button',
                {
                    onClick: () => {
                        this.setState({date: null, isPressed: false, loaded: false});
                        hideData();
                    }
                },
                this.hideText)
        );
    }
}

const dom3Container = document.querySelector('#pictures_from_date_button_container');
ReactDOM.render(e(PicturesFromDateButton), dom3Container);
